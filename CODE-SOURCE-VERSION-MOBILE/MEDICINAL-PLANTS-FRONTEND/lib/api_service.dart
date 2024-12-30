import 'dart:convert';
import 'package:http/http.dart' as http;
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'plante.dart';

class ApiService {
  static const String baseUrl =
      'http://192.168.1.4:8080/api'; // Update this with your actual base URL
  static const String basecommentUrl =
      'http://192.168.1.4:8080'; // Update this with your actual base URL

  static final FlutterSecureStorage _secureStorage = FlutterSecureStorage();

  // Fetch all plants
  static Future<List<Plante>> fetchPlantes() async {
    final Uri fetchUri = Uri.parse('$baseUrl/plantes');
    final headers = await authHeader(); // Get the authorization header

    final response = await http.get(fetchUri, headers: headers);

    if (response.statusCode == 200) {
      List jsonResponse = json.decode(response.body);
      return jsonResponse.map((plante) => Plante.fromJson(plante)).toList();
    } else {
      throw Exception('Failed to load plantes');
    }
  }

  // Search for plants based on parameters
  static Future<List<Plante>> searchPlantes({
    String? nom,
    String? regionGeo,
    String? proprietes,
    String? utilisation,
  }) async {
    final Uri searchUri = Uri.parse('$baseUrl/plantes/search').replace(
        queryParameters: {
      'nom': nom,
      'regionGeo': regionGeo,
      'proprietes': proprietes,
      'utilisation': utilisation,
    }..removeWhere((key, value) =>
            value == null || value.isEmpty)); // Remove null or empty params

    final headers = await authHeader(); // Get the authorization header

    final response = await http.get(searchUri, headers: headers);

    if (response.statusCode == 200) {
      List jsonResponse = json.decode(response.body);
      return jsonResponse.map((plante) => Plante.fromJson(plante)).toList();
    } else {
      throw Exception('Failed to search plantes');
    }
  }

  // Recommend plants based on input criteria
  static Future<List<Plante>> recommendPlantes(
      Map<String, String> params) async {
    // Remove empty or null parameters before constructing the URI
    params.removeWhere((key, value) => value.isEmpty);

    final Uri recommendUri = Uri.parse('$baseUrl/plantes/recommend')
        .replace(queryParameters: params);

    final headers = await authHeader(); // Get the authorization header

    final response = await http.get(recommendUri, headers: headers);

    if (response.statusCode == 200) {
      List jsonResponse = json.decode(response.body);
      return jsonResponse.map((plante) => Plante.fromJson(plante)).toList();
    } else {
      throw Exception('Failed to recommend plantes');
    }
  }

  // Add a comment to a plant
  static Future<Map<String, dynamic>> addComment(
      {required String content, required int plantId}) async {
    // Check if the access token is available and valid
    final String? accessToken = await getAccessToken();
    if (accessToken == null || accessToken.isEmpty) {
      throw Exception('Access token is missing or invalid');
    }

    final Uri addCommentUri = Uri.parse('$basecommentUrl/comments/add');
    final headers = await authHeader(); // Get the authorization header

    final Map<String, dynamic> body = {
      'content': content,
      'plantId': plantId,
    };

    try {
      final response = await http.post(
        addCommentUri,
        headers: {
          ...headers,
          'Content-Type': 'application/json',
        },
        body: json.encode(body),
      );

      // Check if the response status is 200 OK
      if (response.statusCode == 200) {
        return json.decode(response.body);
      } else {
        // If the response status code is not 200, log the error
        print('Error: ${response.statusCode} - ${response.body}');
        throw Exception('Failed to add comment: ${response.body}');
      }
    } catch (e) {
      // Catch any errors and print/log them
      print('Error while adding comment: $e');
      throw Exception('Failed to add comment: $e');
    }
  }

// Get comments by plant ID
  static Future<List<Map<String, dynamic>>> getCommentsByPlant(
      int plantId) async {
    // Check if the access token is available and valid
    final String? accessToken = await getAccessToken();
    if (accessToken == null || accessToken.isEmpty) {
      throw Exception('Access token is missing or invalid');
    }

    final Uri commentsUri = Uri.parse('$basecommentUrl/comments/$plantId');
    final headers = await authHeader(); // Get the authorization header

    try {
      final response = await http.get(commentsUri, headers: headers);

      // Check if the response status is 200 OK
      if (response.statusCode == 200) {
        List jsonResponse = json.decode(response.body);
        return jsonResponse.cast<Map<String, dynamic>>();
      } else {
        // If the response status code is not 200, log the error
        print('Error: ${response.statusCode} - ${response.body}');
        throw Exception('Failed to fetch comments: ${response.body}');
      }
    } catch (e) {
      // Catch any errors and print/log them
      print('Error while fetching comments: $e');
      throw Exception('Failed to fetch comments: $e');
    }
  }

  // Register user
  static Future<String> registerUser({
    required String username,
    required String email,
    required String password,
  }) async {
    final Uri registerUri = Uri.parse('$baseUrl/register');

    final Map<String, String> body = {
      'username': username,
      'email': email,
      'password': password,
    };

    final response = await http.post(
      registerUri,
      headers: {'Content-Type': 'application/json'},
      body: json.encode(body),
    );

    if (response.statusCode == 200) {
      return 'User registered successfully!';
    } else {
      throw Exception('Failed to register user');
    }
  }

  // Login functionality using POST request with username and password as query parameters
  static Future<bool> login(String username, String password) async {
    final Uri loginUri = Uri.parse('$baseUrl/login').replace(queryParameters: {
      'username': username,
      'password': password,
    });

    final response = await http.post(loginUri);

    if (response.statusCode == 200) {
      final Map<String, dynamic> data = json.decode(response.body);
      String accessToken = data['accessToken'];
      String refreshToken = data['refreshToken'];

      await storeTokens(accessToken, refreshToken);

      return true;
    } else {
      throw Exception('Login failed');
    }
  }

  // Save Tokens Securely
  static Future<void> storeTokens(
      String accessToken, String refreshToken) async {
    await _secureStorage.write(key: 'accessToken', value: accessToken);
    await _secureStorage.write(key: 'refreshToken', value: refreshToken);
  }

  static Future<String?> getAccessToken() async {
    return await _secureStorage.read(key: 'accessToken');
  }

  static Future<String?> getRefreshToken() async {
    return await _secureStorage.read(key: 'refreshToken');
  }

  static Future<void> clearTokens() async {
    await _secureStorage.delete(key: 'accessToken');
    await _secureStorage.delete(key: 'refreshToken');
  }

  // Generate authorization header
  static Future<Map<String, String>> authHeader() async {
    final String? accessToken = await getAccessToken();
    if (accessToken == null) {
      throw Exception('No access token found');
    }

    return {'Authorization': 'Bearer $accessToken'};
  }

  // Refresh access token if expired
  static Future<void> refreshAccessToken() async {
    final String? refreshToken = await getRefreshToken();
    if (refreshToken == null) {
      throw Exception('No refresh token found');
    }

    final Uri refreshUri = Uri.parse('$baseUrl/refresh-token');
    final response = await http.post(
      refreshUri,
      headers: {'Content-Type': 'application/json'},
      body: json.encode({'refreshToken': refreshToken}),
    );

    if (response.statusCode == 200) {
      final Map<String, dynamic> data = json.decode(response.body);
      String newAccessToken = data['accessToken'];

      await storeTokens(newAccessToken, refreshToken);
    } else {
      throw Exception('Failed to refresh access token');
    }
  }

  // Logout
  static Future<void> logout() async {
    await clearTokens();
  }
}
