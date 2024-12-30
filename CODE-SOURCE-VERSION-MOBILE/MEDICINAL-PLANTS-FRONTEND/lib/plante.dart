class Plante {
  final int id;
  final String nom;
  final String description;
  final String proprietes;
  final String utilisation;
  final String precautions;
  final String interactions;
  final String principaleImage;
  final String image1;
  final String image2;
  final String image3;
  final String videoLink;
  final String articleLink;
  final String regionGeo;

  Plante({
    required this.id,
    required this.nom,
    required this.description,
    required this.proprietes,
    required this.utilisation,
    required this.precautions,
    required this.interactions,
    required this.principaleImage,
    required this.image1,
    required this.image2,
    required this.image3,
    required this.videoLink,
    required this.articleLink,
    required this.regionGeo,
  });

  factory Plante.fromJson(Map<String, dynamic> json) {
    return Plante(
      id: json['id'],
      nom: json['nom'],
      description: json['description'],
      proprietes: json['proprietes'],
      utilisation: json['utilisation'],
      precautions: json['precautions'],
      interactions: json['interactions'],
      principaleImage: json['principaleImage'],
      image1: json['image1'],
      image2: json['image2'],
      image3: json['image3'],
      videoLink: json['videoLink'],
      articleLink: json['articleLink'],
      regionGeo: json['regionGeo'],
    );
  }
}
