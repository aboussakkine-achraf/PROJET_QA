import 'package:flutter/material.dart';

class CustomNavigationBar extends StatelessWidget {
  final Function(int) onItemTapped;
  final int selectedIndex;

  CustomNavigationBar({
    required this.onItemTapped,
    required this.selectedIndex,
  });

  @override
  Widget build(BuildContext context) {
    return Material(
      color: Colors
          .transparent, // Set to transparent to avoid the default background
      child: BottomNavigationBar(
        type: BottomNavigationBarType.fixed,
        backgroundColor: Color(0xFF5C5470),
        selectedItemColor: Color(0xFFDBD8E3),
        unselectedItemColor: Colors.white,
        currentIndex: selectedIndex,
        onTap: onItemTapped,
        items: [
          BottomNavigationBarItem(
            icon: _buildIcon('assets/home.png', 0), // Home icon
            label: '',
          ),
          BottomNavigationBarItem(
            icon: _buildIcon('assets/plant.png', 1), // Plant icon
            label: '',
          ),
          BottomNavigationBarItem(
            icon: _buildIcon('assets/ai.png', 2), // AI icon
            label: '',
          ),
          BottomNavigationBarItem(
            icon: _buildIcon('assets/profile.png', 3), // Profile icon
            label: '',
          ),
        ],
        iconSize: 24, // Keep the icon size the same
        selectedFontSize: 0, // Remove the label text
        unselectedFontSize: 0, // Remove the label text
      ),
    );
  }

  // This function creates a circle around the icon if it's selected
  Widget _buildIcon(String assetPath, int index) {
    return Material(
      color: Colors
          .transparent, // Set to transparent to prevent any material effect
      child: InkWell(
        splashColor: Colors.transparent, // Disable splash ripple effect
        highlightColor:
            Colors.transparent, // Disable highlight color when tapped
        onTap: () {
          onItemTapped(index); // Handle tab change on icon tap
        },
        child: Container(
          padding: EdgeInsets.symmetric(
              vertical: 8,
              horizontal: 8), // Vertical padding added to center the icon
          decoration: BoxDecoration(
            shape: BoxShape.circle,
            color:
                selectedIndex == index ? Color(0xFFDBD8E3) : Colors.transparent,
          ),
          child: Image.asset(
            assetPath,
            height: 24,
            color: selectedIndex == index
                ? Colors.black
                : null, // Color for selected icon
          ),
        ),
      ),
    );
  }
}
