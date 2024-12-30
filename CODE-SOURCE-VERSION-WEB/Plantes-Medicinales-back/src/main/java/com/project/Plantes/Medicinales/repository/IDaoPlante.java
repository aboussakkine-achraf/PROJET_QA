package com.project.Plantes.Medicinales.repository;

import com.project.Plantes.Medicinales.entities.Plante;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IDaoPlante extends JpaRepository<Plante, Long> {

    //Optional<Plante> findByNom(String nom);
    List<Plante> findByNomContaining(String nom);
    List<Plante> findByProprietesContaining(String proprietes);
    List<Plante> findByUtilisationContaining(String utilisation);
    List<Plante> findByPreaucautionContaining(String precaution);


}
