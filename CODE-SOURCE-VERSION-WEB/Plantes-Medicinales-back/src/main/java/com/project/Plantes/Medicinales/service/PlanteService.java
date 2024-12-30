package com.project.Plantes.Medicinales.service;

import com.project.Plantes.Medicinales.entities.Plante;
import com.project.Plantes.Medicinales.entities.User;
import com.project.Plantes.Medicinales.repository.IDaoPlante;
import com.project.Plantes.Medicinales.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service

public class PlanteService {



    private final String uploadDir = "src/main/resources/static/images/";

    @Autowired
    private IDaoPlante planteRepository;





    public Plante ajouterPlante(Plante plante, MultipartFile imageFile) throws IOException {
        String imagePath = saveImage(imageFile);
        plante.setImage(imagePath);
        return planteRepository.save(plante);
    }



    public List<Plante> afficherToutesLesPlantes() {
        return planteRepository.findAll();
    }

    /*
    public Optional<Plante> rechercherParNom(String nom ) {


            return planteRepository.findByNom(nom);

    }

     */
    public List<Plante> rechercherParNom(String nom) {
        return planteRepository.findByNomContaining(nom);
    }

    public List<Plante> rechercheParPropriete(String proprietes) {
        return planteRepository.findByProprietesContaining(proprietes);
    }

    public List<Plante> rechercheParUtilisation(String utilisation) {
        return planteRepository.findByUtilisationContaining(utilisation);
    }

    public List<Plante> rechercheParPreaucaution(String precaution){

        return planteRepository.findByPreaucautionContaining(precaution);
    }

    /*
    public List<Plante> recommenderPlantes(String proprietes, String precaution) {

        List<Plante> plantesAvecPropriete = planteRepository.findByProprietesContaining(proprietes);

        return plantesAvecPropriete.stream()
                .filter(plante -> plante.getPreaucaution() == null || !plante.getPreaucaution().contains(precaution))
                .toList();
    }

     */

    public Map<String, List<Plante>> recommenderPlantes(String proprietes, String precaution) {
        // Récupérer toutes les plantes ayant la propriété spécifiée
        List<Plante> plantesAvecPropriete = planteRepository.findByProprietesContaining(proprietes);

        // Filtrer les plantes en deux catégories
        List<Plante> plantesSansPrecaution = plantesAvecPropriete.stream()
                .filter(plante -> plante.getPreaucaution() == null || !plante.getPreaucaution().contains(precaution))
                .collect(Collectors.toList());

        List<Plante> plantesAvecPrecaution = plantesAvecPropriete.stream()
                .filter(plante -> plante.getPreaucaution() != null && plante.getPreaucaution().contains(precaution))
                .collect(Collectors.toList());

        // Retourner les deux listes dans une map
        Map<String, List<Plante>> result = new HashMap<>();
        result.put("PlantesSansPrecaution", plantesSansPrecaution);
        result.put("PlantesAvecPrecaution", plantesAvecPrecaution);

        return result;
    }




    public Optional<Plante> modifierPlante(Long id, Plante plante, MultipartFile imageFile) throws IOException {
        return planteRepository.findById(id).map(existingPlante -> {
            existingPlante.setNom(plante.getNom());
            existingPlante.setDescription(plante.getDescription());
            existingPlante.setProprietes(plante.getProprietes());
            existingPlante.setUtilisation(plante.getUtilisation());
            existingPlante.setPreaucaution(plante.getPreaucaution());

            // Mettre à jour l'image si un fichier est fourni
            if (imageFile != null && !imageFile.isEmpty()) {
                try {
                    String imagePath = saveImage(imageFile);
                    existingPlante.setImage(imagePath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return planteRepository.save(existingPlante);
        });
    }

    public void supprimerPlante(Long id) {
        planteRepository.deleteById(id);
    }

    private String saveImage(MultipartFile imageFile) throws IOException {
        if (imageFile == null || imageFile.isEmpty()) {
            return null;
        }
        Files.createDirectories(Paths.get(uploadDir)); //garantit que repertoire cible existe avant d'essayer de sauvegarder l'image
        String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename(); //  fileName le nom du ficihier avec un timestamp par exemple 1679512345678_nomImage.jpg.
        Path filePath = Paths.get(uploadDir + fileName); // concatenation du chemin et le nom du fichier pour obtenir le chemin complet src/main/resources/static/images/1679512345678_nomImage.jpg
        Files.write(filePath, imageFile.getBytes()); // pour sauvegarder un fichier
        return "/images/" + fileName; // les images sont accessible via ..../images/
    }


    /*
    public Optional<Plante> ajouterMessageALaPlante(Long id, String message) {
        Optional<Plante> plante = planteRepository.findById(id);

        if (plante.isPresent()) {
            Plante existingPlante = plante.get();
            existingPlante.getMessage().add(message); // Add the message to the existing collection
            planteRepository.save(existingPlante);    // Save the updated entity
            return Optional.of(existingPlante);
        } else {
            return Optional.empty();
        }
    }

     */


    /*
    public Optional<Plante> affcecterMessagePlante(Long id , String message){

       Optional<Plante> plante = planteRepository.findById(id);
       if (plante.isPresent()) {
           Plante plante1 = plante.get();
           plante1.setMessage(message);
           return Optional.of(planteRepository.save(plante1));
       }else {
           return Optional.empty();
       }
    }

     */
}
