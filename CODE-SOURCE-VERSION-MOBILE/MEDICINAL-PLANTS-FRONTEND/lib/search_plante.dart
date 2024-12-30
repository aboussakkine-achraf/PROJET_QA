import 'package:flutter/material.dart';

class SearchPlante extends StatelessWidget {
  final ValueChanged<String> onSearchChanged;
  final FocusNode focusNode; // Add the focusNode

  const SearchPlante({
    Key? key,
    required this.onSearchChanged,
    required this.focusNode, // Accept the focusNode as a parameter
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.all(8.0),
      child: TextField(
        focusNode: focusNode, // Assign the focusNode to the TextField
        onChanged: onSearchChanged,
        decoration: InputDecoration(
          hintText: 'Rechercher une plante',
          prefixIcon: Icon(Icons.search),
          border: OutlineInputBorder(
            borderRadius: BorderRadius.circular(8.0),
          ),
        ),
      ),
    );
  }
}
