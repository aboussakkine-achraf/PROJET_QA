import 'package:flutter/material.dart';
import 'plante.dart';
import 'plant_details.dart'; // Import the PlantDetails screen

class PlanteCard extends StatelessWidget {
  final Plante plante;

  const PlanteCard({Key? key, required this.plante}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return GestureDetector(
      onTap: () {
        // Navigate to PlantDetails page and pass the 'plante' object
        Navigator.push(
          context,
          MaterialPageRoute(
            builder: (context) => PlantDetails(plante: plante),
          ),
        );
      },
      child: Card(
        elevation: 4,
        margin: EdgeInsets.all(8),
        shape: RoundedRectangleBorder(
          borderRadius: BorderRadius.circular(12),
        ),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.center,
          children: [
            ClipRRect(
              borderRadius: BorderRadius.only(
                topLeft: Radius.circular(12),
                topRight: Radius.circular(12),
              ),
              child: Image.network(
                plante.principaleImage,
                fit: BoxFit.cover,
                height: 100,
                width: double.infinity,
              ),
            ),
            SizedBox(
                height:
                    18), // Adjusted the space between the image and the name
            Padding(
              padding: const EdgeInsets.symmetric(horizontal: 8.0),
              child: Text(
                plante.nom,
                style: TextStyle(
                  fontWeight: FontWeight.bold,
                  fontSize: 16,
                ),
                textAlign: TextAlign.center,
              ),
            ),
            SizedBox(
                height:
                    8), // Added some padding below the name for better spacing
          ],
        ),
      ),
    );
  }
}
