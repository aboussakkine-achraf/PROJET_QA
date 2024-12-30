package com.project.repository;

import com.project.entity.PlantesMedicinales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlantesMedicinalesRepository extends JpaRepository<PlantesMedicinales, Long> {
    // Existing search method (used for general search)
    List<PlantesMedicinales> findByNomContainingIgnoreCaseOrRegionGeoContainingIgnoreCaseOrProprietesContainingIgnoreCaseOrUtilisationContainingIgnoreCase(
            String nom, String regionGeo, String proprietes, String utilisation);

    // New method for personalized recommendation search (used for form inputs)
    List<PlantesMedicinales> findByDescriptionContainingIgnoreCaseOrUtilisationContainingIgnoreCaseOrPrecautionsContainingIgnoreCaseOrInteractionsContainingIgnoreCaseOrProprietesContainingIgnoreCase(
            String description, String utilisation, String precautions, String interactions, String proprietes);
}
