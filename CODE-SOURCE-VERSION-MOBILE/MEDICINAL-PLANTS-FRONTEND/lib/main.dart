import 'package:flutter/material.dart';
import 'signup_screen.dart'; // Import the SignUpScreen
import 'plante_list_screen.dart';
import 'recommandform.dart';
import 'CustomNavigationBar.dart'; // Import your custom navigation bar
import 'AiScreen.dart';
import 'ProfileScreen.dart';
import 'token_check_screen.dart'; // Import the TokenCheckScreen

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Plantes Medicinales',
      theme: ThemeData(
        primarySwatch: Colors.green,
      ),
      home: TokenCheckScreen(), // Set TokenCheckScreen as the initial screen
    );
  }
}

class MainScreen extends StatefulWidget {
  @override
  _MainScreenState createState() => _MainScreenState();
}

class _MainScreenState extends State<MainScreen> {
  int _selectedIndex = 0;

  // Define the screens for each tab
  final List<Widget> _screens = [
    PlanteListScreen(), // Home screen
    RecommandForm(), // Plant screen
    AiScreen(),
    ProfileScreen(),
  ];

  void _onTabTapped(int index) {
    setState(() {
      _selectedIndex = index;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: _screens[_selectedIndex], // Display the selected screen
      bottomNavigationBar: CustomNavigationBar(
        onItemTapped: _onTabTapped, // Pass the tab index change function
        selectedIndex: _selectedIndex, // Set the selected tab
      ),
    );
  }
}
