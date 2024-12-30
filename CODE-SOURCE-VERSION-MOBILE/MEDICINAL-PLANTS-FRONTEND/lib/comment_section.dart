import 'package:flutter/material.dart';
import 'api_service.dart'; // Import the ApiService
import 'plante.dart'; // Assuming the Plante class is used for plant-related models

class CommentSection extends StatefulWidget {
  final int plantId; // Declare the plantId parameter

  CommentSection({Key? key, required this.plantId}) : super(key: key);

  @override
  _CommentSectionState createState() => _CommentSectionState();
}

class _CommentSectionState extends State<CommentSection> {
  final TextEditingController _commentController = TextEditingController();

  // Function to add a comment
  Future<void> _addComment() async {
    String content = _commentController.text;

    if (content.isNotEmpty) {
      try {
        // Call the API to add the comment
        await ApiService.addComment(content: content, plantId: widget.plantId);
        _commentController.clear();
        // After comment is added, fetch the latest comments
        setState(() {});
      } catch (e) {
        print('Error adding comment: $e');
        // You can show a snackbar or dialog to alert the user here
      }
    }
  }

  // Function to fetch comments
  Future<List<Map<String, dynamic>>> _fetchComments() async {
    try {
      return await ApiService.getCommentsByPlant(widget.plantId);
    } catch (e) {
      print('Error fetching comments: $e');
      return []; // Return an empty list if an error occurs
    }
  }

  @override
  Widget build(BuildContext context) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        // Comment input section
        Padding(
          padding: const EdgeInsets.symmetric(vertical: 10, horizontal: 16),
          child: Row(
            children: [
              Expanded(
                child: TextField(
                  controller: _commentController,
                  decoration: InputDecoration(
                    labelText: 'Add a comment...',
                    border: OutlineInputBorder(),
                  ),
                ),
              ),
              IconButton(
                icon: Icon(Icons.send),
                onPressed: _addComment,
              ),
            ],
          ),
        ),

        // Display comments section
        FutureBuilder<List<Map<String, dynamic>>>(
          // FutureBuilder to fetch comments
          future: _fetchComments(),
          builder: (context, snapshot) {
            if (snapshot.connectionState == ConnectionState.waiting) {
              return Center(child: CircularProgressIndicator());
            }

            if (snapshot.hasError) {
              return Center(child: Text('Error fetching comments.'));
            }

            if (!snapshot.hasData || snapshot.data!.isEmpty) {
              return Padding(
                padding:
                    const EdgeInsets.symmetric(vertical: 20, horizontal: 16),
                child: Text("Be the first to comment!",
                    style: TextStyle(fontSize: 16)),
              );
            }

            // Displaying comment list
            return ListView.builder(
              shrinkWrap: true,
              physics: NeverScrollableScrollPhysics(),
              itemCount: snapshot.data!.length,
              itemBuilder: (context, index) {
                var comment = snapshot.data![index];
                return Card(
                  margin: EdgeInsets.symmetric(vertical: 8, horizontal: 16),
                  child: ListTile(
                    title: Text(comment['username']), // Display username
                    subtitle:
                        Text(comment['content']), // Display comment content
                  ),
                );
              },
            );
          },
        ),
      ],
    );
  }
}
