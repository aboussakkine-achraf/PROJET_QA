package com.project.Plantes.Medicinales.controller;

import com.project.Plantes.Medicinales.entities.Article;
import com.project.Plantes.Medicinales.entities.Commentaire;
import com.project.Plantes.Medicinales.entities.Plante;
import com.project.Plantes.Medicinales.entities.User;
import com.project.Plantes.Medicinales.repository.CommentaireRepository;
import com.project.Plantes.Medicinales.repository.IDaoPlante;
import com.project.Plantes.Medicinales.repository.UserRepository;
import com.project.Plantes.Medicinales.responses.ArticleFlatResponse;
import com.project.Plantes.Medicinales.responses.CommentResponseDTO;
import com.project.Plantes.Medicinales.service.PlanteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/plantes")
public class PlanteController {

    @Autowired
    private PlanteService planteService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentaireRepository commentaireRepository;

    @Autowired
    private IDaoPlante planteRepository;


    @PostMapping("/ajouter")
    public ResponseEntity<Plante> ajouterPlante(
            @RequestParam("nom") String nom,
            @RequestParam("description") String description,
            @RequestParam("proprietes") String proprietes,
            @RequestParam("utilisation") String utilisation,
            @RequestParam("preaucaution") String preaucaution,
            @RequestParam("image") MultipartFile imageFile,
            Authentication authentication
    ) {
        try {
            // Get the currently authenticated user
            String currentUsername = authentication.getName();
            User user = userRepository.findByEmail(currentUsername)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // Create the Plante object
            Plante plante = new Plante();
            plante.setNom(nom);
            plante.setDescription(description);
            plante.setProprietes(proprietes);
            plante.setUtilisation(utilisation);
            plante.setPreaucaution(preaucaution);
            plante.setUser(user);



            // Save the Plante
            Plante savedPlante = planteService.ajouterPlante(plante, imageFile);
            return ResponseEntity.ok(savedPlante);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }


    @GetMapping("/afficher")
    public List<Plante> afficherToutesLesPlantes() {
        return planteService.afficherToutesLesPlantes();
    }



    @GetMapping
    public ResponseEntity<List<Plante>> getAllPlantes() {
        List<Plante> plantes = planteService.afficherToutesLesPlantes();

        // Convertir chaque article en ArticleFlatResponse


        return ResponseEntity.ok(plantes);
    }

    @GetMapping("/rechercher")
    public ResponseEntity<List<Plante>> rechercherParNom(@RequestParam String nom) {
        List<Plante> plantes = planteService.rechercherParNom(nom);
        if (plantes.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(plantes);
    }

    @GetMapping("/rechercherproprietes")
    public ResponseEntity<List<Plante>> rechercheParProprietes(@RequestParam String proprietes) {
        List<Plante> plantes = planteService.rechercheParPropriete(proprietes);
        if (plantes.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(plantes);
    }

    @GetMapping("/rechercherutilisation")
    public ResponseEntity<List<Plante>> rechercheParUtilisation(@RequestParam String utilisation) {
        List<Plante> plantes = planteService.rechercheParUtilisation(utilisation);
        if (plantes.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(plantes);
    }

    @GetMapping("/rechercherprecaution")
    public ResponseEntity<List<Plante>> rechercherprecaution(@RequestParam String preaucaution) {
        List<Plante> plantes = planteService.rechercheParPreaucaution(preaucaution);
        if (plantes.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(plantes);
    }

    @PutMapping("/modifier/{id}")
    public ResponseEntity<Plante> modifierPlante(
            @PathVariable Long id,
            @RequestParam("nom") String nom,
            @RequestParam("description") String description,
            @RequestParam("proprietes") String proprietes,
            @RequestParam("utilisation") String utilisation,
            @RequestParam("preaucaution") String preaucaution,
            @RequestParam(value = "image", required = false) MultipartFile imageFile
    ) {
        try {
            Plante plante = new Plante();
            plante.setNom(nom);
            plante.setDescription(description);
            plante.setProprietes(proprietes);
            plante.setUtilisation(utilisation);
            plante.setPreaucaution(preaucaution);

            Optional<Plante> updatedPlante = planteService.modifierPlante(id, plante, imageFile);
            return updatedPlante.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @DeleteMapping("/supprimer/{id}")
    public ResponseEntity<Void> supprimerPlante(@PathVariable Long id) {
        planteService.supprimerPlante(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/recommender")
    public ResponseEntity<Map<String, List<Plante>>> recommenderPlantes(
            @RequestParam String proprietes,
            @RequestParam String precaution) {
        Map<String, List<Plante>> result = planteService.recommenderPlantes(proprietes, precaution);
        if (result.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }


/*
    @PostMapping("/plantes/{planteId}/add-comment")
    public ResponseEntity<CommentResponseDTO> addComment(
            @PathVariable Long planteId,
            @RequestParam("content") String content,
            Authentication authentication) {
        try {

            String currentUsername = authentication.getName();
            User user = userRepository.findByEmail(currentUsername)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // Trouver la plante
            Plante plante = planteRepository.findById(planteId)
                    .orElseThrow(() -> new RuntimeException("Plante not found"));

            // Créer le commentaire
            Commentaire commentaire = new Commentaire();
            commentaire.setContent(content);
            commentaire.setPlante(plante);
            commentaire.setUser(user);

            // Sauvegarder le commentaire
            Commentaire savedComment = commentaireRepository.save(commentaire);

            // Créer une réponse DTO
            CommentResponseDTO responseDTO = new CommentResponseDTO(
                    savedComment.getId(),
                    savedComment.getContent(),
                    user.getFullName() // Assurez-vous que `getFullName()` existe dans l'entité User
            );

            // Retourner la réponse
            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }


 */

    @PostMapping("/plantes/{planteId}/add-comment")
    public ResponseEntity<CommentResponseDTO> addComment(
            @PathVariable Long planteId,
            @RequestBody Map<String, String> contentData,  // Utilisez Map pour recevoir le corps de la requête
            Authentication authentication) {
        try {
            String content = contentData.get("content");  // Récupérez le contenu du commentaire

            // Le reste du code reste inchangé
            String currentUsername = authentication.getName();
            User user = userRepository.findByEmail(currentUsername)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // Trouver la plante et ajouter le commentaire
            Plante plante = planteRepository.findById(planteId)
                    .orElseThrow(() -> new RuntimeException("Plante not found"));

            Commentaire commentaire = new Commentaire();
            commentaire.setContent(content);
            commentaire.setPlante(plante);
            commentaire.setUser(user);

            Commentaire savedComment = commentaireRepository.save(commentaire);

            CommentResponseDTO responseDTO = new CommentResponseDTO(
                    savedComment
            );

            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }


    @GetMapping("/plantes/{planteId}/comments")
    public ResponseEntity<List<CommentResponseDTO>> getCommentsForPlante(
            @PathVariable Long planteId) {
        try {
            // Trouver la plante
            Plante plante = planteRepository.findById(planteId)
                    .orElseThrow(() -> new RuntimeException("Plante not found"));

            // Récupérer les commentaires associés
            List<Commentaire> commentaires = commentaireRepository.findByPlanteId(planteId);

            // Transformer les commentaires en CommentResponseDTO
            List<CommentResponseDTO> responseDTOs = commentaires.stream()
                    .map(comment -> new CommentResponseDTO(comment)) // Utiliser chaque commentaire individuellement
                    .toList();

            // Retourner la liste des DTO
            return ResponseEntity.ok(responseDTOs);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }












}
