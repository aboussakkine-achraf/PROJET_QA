package com.project.controller;

import com.project.entity.PlantesMedicinales;
import com.project.service.PlantesMedicinalesServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/plantes")
public class PlantesMedicinalesController {

    private final PlantesMedicinalesServiceImpl plantesMedicinalesService;

    @Autowired
    public PlantesMedicinalesController(PlantesMedicinalesServiceImpl plantesMedicinalesService) {
        this.plantesMedicinalesService = plantesMedicinalesService;
    }

    @PostMapping
    public ResponseEntity<PlantesMedicinales> createPlante(@RequestBody PlantesMedicinales plante) {
        PlantesMedicinales createdPlante = plantesMedicinalesService.createPlante(plante);
        return ResponseEntity.ok(createdPlante);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlantesMedicinales> updatePlante(@PathVariable Long id, @RequestBody PlantesMedicinales plante) {
        PlantesMedicinales updatedPlante = plantesMedicinalesService.updatePlante(id, plante);
        return ResponseEntity.ok(updatedPlante);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlantesMedicinales> getPlanteById(@PathVariable Long id) {
        PlantesMedicinales plante = plantesMedicinalesService.getPlanteById(id);
        return ResponseEntity.ok(plante);
    }

    @GetMapping
    public ResponseEntity<List<PlantesMedicinales>> getAllPlantes() {
        List<PlantesMedicinales> plantes = plantesMedicinalesService.getAllPlantes();
        return ResponseEntity.ok(plantes);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlante(@PathVariable Long id) {
        plantesMedicinalesService.deletePlante(id);
        return ResponseEntity.noContent().build();
    }

    // Search endpoint for existing search functionality
    @GetMapping("/search")
    public ResponseEntity<List<PlantesMedicinales>> searchPlantes(
            @RequestParam(required = false) String nom,
            @RequestParam(required = false) String regionGeo,
            @RequestParam(required = false) String proprietes,
            @RequestParam(required = false) String utilisation) {

        List<PlantesMedicinales> results = plantesMedicinalesService.searchPlantes(nom, regionGeo, proprietes, utilisation);
        return ResponseEntity.ok(results);
    }

    // New endpoint for personalized plant recommendations
    @GetMapping("/recommend")
    public ResponseEntity<List<PlantesMedicinales>> recommendPlantes(
            @RequestParam(required = false) String description,
            @RequestParam(required = false) String utilisation,
            @RequestParam(required = false) String precautions,
            @RequestParam(required = false) String interactions,
            @RequestParam(required = false) String proprietes) {

        // Call the service method to fetch the recommended plants based on the criteria
        List<PlantesMedicinales> recommendedPlantes = plantesMedicinalesService.recommendPlantes(description, utilisation, precautions, interactions, proprietes);
        return ResponseEntity.ok(recommendedPlantes);
    }
}
