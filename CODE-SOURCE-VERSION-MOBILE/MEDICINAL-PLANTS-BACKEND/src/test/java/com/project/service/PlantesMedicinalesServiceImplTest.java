package com.project.service;

import com.project.entity.PlantesMedicinales;
import com.project.repository.PlantesMedicinalesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlantesMedicinalesServiceImplTest {

    @Mock
    private PlantesMedicinalesRepository repository;

    @InjectMocks
    private PlantesMedicinalesServiceImpl service;

    private PlantesMedicinales plante;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        plante = new PlantesMedicinales();
        plante.setId(1L);
        plante.setNom("Aloe Vera");
        plante.setRegionGeo("Africa");
        plante.setProprietes("Healing properties");
        plante.setUtilisation("Skin treatment");
    }

    @Test
    void testCreatePlante() {
        when(repository.save(any(PlantesMedicinales.class))).thenReturn(plante);

        PlantesMedicinales result = service.createPlante(plante);

        assertNotNull(result);
        assertEquals(plante.getNom(), result.getNom());
        verify(repository, times(1)).save(plante);
    }

    @Test
    void testUpdatePlante() {
        when(repository.existsById(1L)).thenReturn(true);
        when(repository.save(any(PlantesMedicinales.class))).thenReturn(plante);

        PlantesMedicinales result = service.updatePlante(1L, plante);

        assertNotNull(result);
        assertEquals(plante.getNom(), result.getNom());
        verify(repository, times(1)).save(plante);
    }

    @Test
    void testUpdatePlanteNotFound() {
        when(repository.existsById(1L)).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> service.updatePlante(1L, plante));
    }

    @Test
    void testGetPlanteById() {
        when(repository.findById(1L)).thenReturn(Optional.of(plante));

        PlantesMedicinales result = service.getPlanteById(1L);

        assertNotNull(result);
        assertEquals(plante.getNom(), result.getNom());
    }

    @Test
    void testGetPlanteByIdNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> service.getPlanteById(1L));
    }

    @Test
    void testGetAllPlantes() {
        when(repository.findAll()).thenReturn(List.of(plante));

        assertFalse(service.getAllPlantes().isEmpty());
        verify(repository, times(1)).findAll();
    }

    @Test
    void testDeletePlante() {
        when(repository.existsById(1L)).thenReturn(true);

        service.deletePlante(1L);

        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void testDeletePlanteNotFound() {
        when(repository.existsById(1L)).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> service.deletePlante(1L));
    }

    @Test
    void testSearchPlantes() {
        when(repository.findByNomContainingIgnoreCaseOrRegionGeoContainingIgnoreCaseOrProprietesContainingIgnoreCaseOrUtilisationContainingIgnoreCase(
                any(), any(), any(), any())).thenReturn(List.of(plante));

        assertFalse(service.searchPlantes("Aloe", null, null, null).isEmpty());
    }

    @Test
    void testRecommendPlantes() {
        when(repository.findByDescriptionContainingIgnoreCaseOrUtilisationContainingIgnoreCaseOrPrecautionsContainingIgnoreCaseOrInteractionsContainingIgnoreCaseOrProprietesContainingIgnoreCase(
                any(), any(), any(), any(), any())).thenReturn(List.of(plante));

        assertFalse(service.recommendPlantes("Healing", "Skin treatment", null, null, null).isEmpty());
    }
}
