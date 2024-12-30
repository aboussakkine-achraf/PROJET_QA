package com.project.Plantes.Medicinales.repository;

import com.project.Plantes.Medicinales.entities.Commentaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentaireRepository extends JpaRepository<Commentaire, Long> {

    @Query("SELECT c FROM Commentaire c WHERE c.plante.id = :planteId")
    List<Commentaire> findByPlanteId(@Param("planteId") Long planteId);




}
