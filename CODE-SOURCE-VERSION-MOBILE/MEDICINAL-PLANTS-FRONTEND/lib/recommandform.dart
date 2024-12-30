import 'package:flutter/material.dart';
import 'api_service.dart';
import 'plante.dart';
import 'recommended_plants_screen.dart'; // Import the RecommendedPlantsScreen

class RecommandForm extends StatefulWidget {
  @override
  _RecommandFormState createState() => _RecommandFormState();
}

class _RecommandFormState extends State<RecommandForm> {
  final _formKey = GlobalKey<FormState>();

  // Define controllers for each input field
  final TextEditingController _symptomsController = TextEditingController();
  final TextEditingController _preferredMethodController =
      TextEditingController();
  final TextEditingController _allergiesController = TextEditingController();
  final TextEditingController _medicationController = TextEditingController();
  final TextEditingController _desiredBenefitsController =
      TextEditingController();

  // Method to handle form submission
  Future<void> _submitForm() async {
    // Collect non-empty form data into a map for flexibility
    Map<String, String> params = {
      'description': _symptomsController.text,
      'utilisation': _preferredMethodController.text,
      'precautions': _allergiesController.text,
      'interactions': _medicationController.text,
      'proprietes': _desiredBenefitsController.text,
    };

    // Remove empty values from the map
    params.removeWhere((key, value) => value.isEmpty);

    if (params.isNotEmpty) {
      try {
        // Call the recommendPlantes method from ApiService with non-empty parameters
        List<Plante> recommendedPlants =
            await ApiService.recommendPlantes(params);

        // Navigate to RecommendedPlantsScreen with the recommendations
        Navigator.push(
          context,
          MaterialPageRoute(
            builder: (context) => RecommendedPlantsScreen(
              recommendedPlantes:
                  recommendedPlants, // Pass the recommended plants
            ),
          ),
        );
      } catch (error) {
        // Handle error (e.g., show a Snackbar or AlertDialog)
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(content: Text('Failed to get recommendations: $error')),
        );
      }
    } else {
      // Show a message if no field is filled
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(
            content:
                Text('Please fill at least one field to get recommendations')),
      );
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Recommendation'),
      ),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: SingleChildScrollView(
          child: Form(
            key: _formKey,
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                // Symptoms Input
                Text(
                  'What are your symptoms?',
                  style: TextStyle(fontSize: 16, fontWeight: FontWeight.bold),
                ),
                TextFormField(
                  controller: _symptomsController,
                  decoration: InputDecoration(
                    hintText: 'Enter your symptoms here',
                    border: OutlineInputBorder(),
                  ),
                  maxLines: 3,
                ),
                SizedBox(height: 16),

                // Preferred Method of Use Input
                Text(
                  'How would you like to use the product?',
                  style: TextStyle(fontSize: 16, fontWeight: FontWeight.bold),
                ),
                TextFormField(
                  controller: _preferredMethodController,
                  decoration: InputDecoration(
                    hintText: 'Enter your preferred method here',
                    border: OutlineInputBorder(),
                  ),
                  maxLines: 3,
                ),
                SizedBox(height: 16),

                // Allergies / Sensitivities Input
                Text(
                  'Do you have any allergies or sensitivities?',
                  style: TextStyle(fontSize: 16, fontWeight: FontWeight.bold),
                ),
                TextFormField(
                  controller: _allergiesController,
                  decoration: InputDecoration(
                    hintText: 'Enter your allergies or sensitivities',
                    border: OutlineInputBorder(),
                  ),
                  maxLines: 3,
                ),
                SizedBox(height: 16),

                // Medication Input
                Text(
                  'What medications are you currently taking?',
                  style: TextStyle(fontSize: 16, fontWeight: FontWeight.bold),
                ),
                TextFormField(
                  controller: _medicationController,
                  decoration: InputDecoration(
                    hintText: 'Enter your medications here',
                    border: OutlineInputBorder(),
                  ),
                  maxLines: 3,
                ),
                SizedBox(height: 16),

                // Desired Benefits Input
                Text(
                  'What benefits are you hoping to achieve?',
                  style: TextStyle(fontSize: 16, fontWeight: FontWeight.bold),
                ),
                TextFormField(
                  controller: _desiredBenefitsController,
                  decoration: InputDecoration(
                    hintText: 'Enter the benefits you desire here',
                    border: OutlineInputBorder(),
                  ),
                  maxLines: 3,
                ),
                SizedBox(height: 24),

                // Submit Button
                Center(
                  child: ElevatedButton(
                    onPressed: _submitForm, // Call the submit function on press
                    child: Text('Submit'),
                  ),
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }
}
