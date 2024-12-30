package com.project.Plantes.Medicinales.service;



import com.project.Plantes.Medicinales.entities.Plante;
import com.project.Plantes.Medicinales.repository.IDaoPlante;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlanteServiceTest {

    @Mock
    private IDaoPlante planteRepository;

    @Mock
    private MultipartFile imageFile;

    @InjectMocks
    private PlanteService planteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialiser les mocks
    }
/*
    @Test
    void testAjouterPlante() throws IOException {
        // Préparation des données
        Plante plante = new Plante();
        plante.setNom("Aloe Vera");

        when(imageFile.isEmpty()).thenReturn(false);
        when(imageFile.getOriginalFilename()).thenReturn("aloe.jpg");
        when(planteRepository.save(any(Plante.class))).thenReturn(plante);

        // Exécution
        Plante result = planteService.ajouterPlante(plante, imageFile);

        // Vérification
        assertNotNull(result);
        assertEquals("Aloe Vera", result.getNom());
        verify(planteRepository, times(1)).save(any(Plante.class));
    }

 */

    @Test
    void testAfficherToutesLesPlantes() {
        // Préparation des données
        Plante plante1 = new Plante();
        Plante plante2 = new Plante();
        when(planteRepository.findAll()).thenReturn(Arrays.asList(plante1, plante2));

        // Exécution
        List<Plante> result = planteService.afficherToutesLesPlantes();

        // Vérification
        assertEquals(2, result.size());
        verify(planteRepository, times(1)).findAll();
    }

    @Test
    void testRechercherParNom() {
        // Préparation des données
        String nom = "Aloe";
        Plante plante = new Plante();
        plante.setNom("Aloe Vera");

        when(planteRepository.findByNomContaining(nom)).thenReturn(Arrays.asList(plante));

        // Exécution
        List<Plante> result = planteService.rechercherParNom(nom);

        // Vérification
        assertEquals(1, result.size());
        assertEquals("Aloe Vera", result.get(0).getNom());
    }

    @Test
    void testModifierPlante() throws IOException {
        // Préparation des données
        Long id = 1L;
        Plante existingPlante = new Plante();
        existingPlante.setId(id);
        existingPlante.setNom("Old Name");

        Plante updatedPlante = new Plante();
        updatedPlante.setNom("New Name");

        when(planteRepository.findById(id)).thenReturn(Optional.of(existingPlante));
        when(planteRepository.save(any(Plante.class))).thenReturn(updatedPlante);

        // Exécution
        Optional<Plante> result = planteService.modifierPlante(id, updatedPlante, null);

        // Vérification
        assertTrue(result.isPresent());
        assertEquals("New Name", result.get().getNom());
    }

    @Test
    void testSupprimerPlante() {
        // Préparation des données
        Long id = 1L;

        doNothing().when(planteRepository).deleteById(id);

        // Exécution
        planteService.supprimerPlante(id);

        // Vérification
        verify(planteRepository, times(1)).deleteById(id);
    }
}

