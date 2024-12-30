import 'package:flutter/material.dart';
import 'plante.dart';
import 'plante_card.dart'; // Import PlanteCard widget

class RecommendedPlantsScreen extends StatelessWidget {
  final List<Plante> recommendedPlantes;

  // Constructor to accept the list of recommended plants
  RecommendedPlantsScreen({Key? key, required this.recommendedPlantes})
      : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Recommended Plants'),
      ),
      body: recommendedPlantes.isEmpty
          ? Center(child: Text('No recommendations found'))
          : SingleChildScrollView(
              child: Column(
                children: [
                  GridView.builder(
                    shrinkWrap: true,
                    physics: NeverScrollableScrollPhysics(),
                    gridDelegate: SliverGridDelegateWithFixedCrossAxisCount(
                      crossAxisCount: 2,
                      childAspectRatio: 1.0,
                    ),
                    itemCount: recommendedPlantes.length,
                    itemBuilder: (context, index) {
                      final plante = recommendedPlantes[index];
                      return PlanteCard(plante: plante);
                    },
                  ),
                ],
              ),
            ),
    );
  }
}
