import 'package:flutter/material.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'api_service.dart'; // Make sure ApiService is imported to use the token check logic
import 'main.dart'; // Import MainScreen
import 'signup_screen.dart'; // Import SignUpScreen

class TokenCheckScreen extends StatefulWidget {
  @override
  _TokenCheckScreenState createState() => _TokenCheckScreenState();
}

class _TokenCheckScreenState extends State<TokenCheckScreen> {
  @override
  void initState() {
    super.initState();
    _checkToken();
  }

  // Check the token and navigate based on its validity
  Future<void> _checkToken() async {
    try {
      // Try to get the access token from secure storage
      String? accessToken = await ApiService.getAccessToken();

      if (accessToken != null && accessToken.isNotEmpty) {
        // If there's a valid token, navigate to the MainScreen
        _navigateToMainScreen();
      } else {
        // If no token found or invalid, navigate to SignUpScreen
        _navigateToSignUpScreen();
      }
    } catch (e) {
      // In case of any error (e.g., no token), navigate to SignUpScreen
      _navigateToSignUpScreen();
    }
  }

  // Navigate to the MainScreen
  void _navigateToMainScreen() {
    Navigator.pushReplacement(
      context,
      MaterialPageRoute(builder: (context) => MainScreen()),
    );
  }

  // Navigate to the SignUpScreen
  void _navigateToSignUpScreen() {
    Navigator.pushReplacement(
      context,
      MaterialPageRoute(builder: (context) => SignUpScreen()),
    );
  }

  @override
  Widget build(BuildContext context) {
    // This screen will display a loading indicator while checking the token
    return Scaffold(
      body: Center(
        child:
            CircularProgressIndicator(), // Show loading spinner while checking the token
      ),
    );
  }
}
