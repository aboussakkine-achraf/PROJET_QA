import 'package:flutter/material.dart';
import 'api_service.dart'; // Import ApiService to access logout method
import 'signup_screen.dart'; // Import SignUpScreen to navigate after logout

class ProfileScreen extends StatelessWidget {
  // Function to handle logout
  Future<void> _logout(BuildContext context) async {
    // Clear the tokens using ApiService
    await ApiService.logout();

    // Navigate to the SignUpScreen after logout
    Navigator.pushReplacement(
      context,
      MaterialPageRoute(builder: (context) => SignUpScreen()),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Profile'),
        actions: [
          // Logout button in the app bar
          IconButton(
            icon: Icon(Icons.logout),
            onPressed: () => _logout(context),
          ),
        ],
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Text(
              'Profile Screen',
              style: TextStyle(fontSize: 24),
            ),
            SizedBox(height: 20),
            ElevatedButton(
              onPressed: () => _logout(context), // Call the logout function
              child: Text('Logout'),
            ),
          ],
        ),
      ),
    );
  }
}
