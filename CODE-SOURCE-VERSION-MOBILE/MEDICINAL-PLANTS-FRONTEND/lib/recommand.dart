import 'package:flutter/material.dart';

class RecommandFooter extends StatelessWidget {
  final VoidCallback onPressed;

  const RecommandFooter({Key? key, required this.onPressed}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Container(
      width: double.infinity,
      padding: EdgeInsets.all(16.0),
      color: Color(0xFF5C5470), // Container background color (#5c5470)
      child: ElevatedButton(
        onPressed: onPressed,
        style: ElevatedButton.styleFrom(
          backgroundColor: Color(0xFFDBD8E3), // Button color (#dbd8e3)
          shape: RoundedRectangleBorder(
            borderRadius: BorderRadius.circular(8),
          ),
          padding: EdgeInsets.symmetric(vertical: 14),
        ),
        child: Text(
          'Personalized Recommendation',
          style: TextStyle(
            fontSize: 16,
            fontWeight: FontWeight.bold,
            color: Colors.black, // Button text color (black for contrast)
          ),
        ),
      ),
    );
  }
}
