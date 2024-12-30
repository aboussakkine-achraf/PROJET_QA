import 'package:flutter/material.dart';
import 'package:url_launcher/url_launcher.dart';
import 'package:youtube_player_flutter/youtube_player_flutter.dart';
import 'package:carousel_slider/carousel_slider.dart'; // Import carousel slider package
import 'plante.dart';
import 'comment_section.dart';

class PlantDetails extends StatefulWidget {
  final Plante plante;

  const PlantDetails({Key? key, required this.plante}) : super(key: key);

  @override
  _PlantDetailsState createState() => _PlantDetailsState();
}

class _PlantDetailsState extends State<PlantDetails> {
  // Boolean states to control "Show more" functionality for each section
  bool showMoreDescription = false;
  bool showMoreProprietes = false;
  bool showMoreUtilisation = false;
  bool showMorePrecautions = false;
  bool showMoreInteractions = false;

  void _openLink(String url) async {
    if (await canLaunch(url)) {
      await launch(url);
    } else {
      throw 'Could not launch $url';
    }
  }

  Widget buildExpandableText(
      String text, bool showFullText, VoidCallback onToggle) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Text(
          showFullText
              ? text
              : (text.length > 100 ? text.substring(0, 100) + '...' : text),
        ),
        TextButton(
          onPressed: onToggle,
          style: TextButton.styleFrom(
            backgroundColor: const Color(0xFF7FFFD4), // Background color
            foregroundColor: Colors.black, // Text color
          ),
          child: Text(showFullText ? "Show less" : "Show more"),
        ),
      ],
    );
  }

  @override
  Widget build(BuildContext context) {
    final plante = widget.plante;

    return Scaffold(
      appBar: AppBar(
        title: Text(plante.nom),
      ),
      body: SingleChildScrollView(
        padding: EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            // Main Image
            Center(
              child: ClipRRect(
                borderRadius: BorderRadius.circular(12),
                child: Image.network(
                  plante.principaleImage,
                  fit: BoxFit.cover,
                  width: double.infinity,
                  height: 200,
                ),
              ),
            ),
            SizedBox(height: 16),

            // Name
            Text(
              plante.nom,
              style: TextStyle(fontSize: 24, fontWeight: FontWeight.bold),
              textAlign: TextAlign.center,
            ),
            SizedBox(height: 16),

            // Description with Show More/Less
            Text(
              'Description',
              style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
            ),
            buildExpandableText(
              plante.description ?? 'N/A',
              showMoreDescription,
              () => setState(() => showMoreDescription = !showMoreDescription),
            ),
            SizedBox(height: 16),

            // Propriétés with Show More/Less
            Text(
              'Properties',
              style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
            ),
            buildExpandableText(
              plante.proprietes ?? 'N/A',
              showMoreProprietes,
              () => setState(() => showMoreProprietes = !showMoreProprietes),
            ),
            SizedBox(height: 16),

            // Utilisation with Show More/Less
            Text(
              'Usage',
              style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
            ),
            buildExpandableText(
              plante.utilisation ?? 'N/A',
              showMoreUtilisation,
              () => setState(() => showMoreUtilisation = !showMoreUtilisation),
            ),
            SizedBox(height: 16),

            // Précautions with Show More/Less
            Text(
              'Precautions',
              style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
            ),
            buildExpandableText(
              plante.precautions ?? 'N/A',
              showMorePrecautions,
              () => setState(() => showMorePrecautions = !showMorePrecautions),
            ),
            SizedBox(height: 16),

            // Interactions with Show More/Less
            Text(
              'Interactions',
              style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
            ),
            buildExpandableText(
              plante.interactions ?? 'N/A',
              showMoreInteractions,
              () =>
                  setState(() => showMoreInteractions = !showMoreInteractions),
            ),
            SizedBox(height: 16),

            // Additional Images using CarouselSlider with border radius
            Text(
              'Additional Images',
              style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
            ),
            SizedBox(height: 8),
            CarouselSlider(
              options: CarouselOptions(
                height: 200,
                autoPlay: false,
                enlargeCenterPage: true,
                aspectRatio: 16 / 9,
                enableInfiniteScroll: false,
              ),
              items: [
                if (plante.image1 != null)
                  ClipRRect(
                    borderRadius:
                        BorderRadius.circular(12), // Border radius added
                    child: Image.network(
                      plante.image1!,
                      fit: BoxFit.cover,
                    ),
                  ),
                if (plante.image2 != null)
                  ClipRRect(
                    borderRadius:
                        BorderRadius.circular(12), // Border radius added
                    child: Image.network(
                      plante.image2!,
                      fit: BoxFit.cover,
                    ),
                  ),
                if (plante.image3 != null)
                  ClipRRect(
                    borderRadius:
                        BorderRadius.circular(12), // Border radius added
                    child: Image.network(
                      plante.image3!,
                      fit: BoxFit.cover,
                    ),
                  ),
              ],
            ),
            SizedBox(height: 16),

            // Region Geo
            Text(
              'Geographic Region',
              style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
            ),
            Text(plante.regionGeo ?? 'N/A'),
            SizedBox(height: 16),

            // Article Link
            if (plante.articleLink != null)
              TextButton(
                onPressed: () => _openLink(plante.articleLink!),
                style: TextButton.styleFrom(
                  backgroundColor: const Color(0xFF7FFFD4), // Background color
                  foregroundColor: Colors.black, // Text color
                ),
                child: Text(
                  'Read more about ${plante.nom}',
                  style: TextStyle(
                      fontSize: 16, color: Color.fromARGB(255, 30, 136, 223)),
                ),
              ),
            SizedBox(height: 16),

            // YouTube Video Title
            if (plante.videoLink != null && plante.videoLink!.isNotEmpty)
              Text(
                'Watch a video about the benefits of ${plante.nom}',
                style: TextStyle(
                  fontSize: 18,
                  fontWeight: FontWeight.bold,
                ),
              ),
            SizedBox(height: 8), // Space between title and video

            // YouTube Video
            if (plante.videoLink != null && plante.videoLink!.isNotEmpty)
              Container(
                height: 200, // Adjust height as needed
                child: YoutubePlayer(
                  controller: YoutubePlayerController(
                    initialVideoId:
                        YoutubePlayer.convertUrlToId(plante.videoLink!)!,
                    flags: const YoutubePlayerFlags(
                      autoPlay: false,
                      mute: false,
                    ),
                  ),
                  showVideoProgressIndicator: true,
                ),
              ),
            SizedBox(height: 16), // Space for comments section
            // Comment Section
            CommentSection(plantId: plante.id), // Pass plant ID
          ],
        ),
      ),
    );
  }
}
