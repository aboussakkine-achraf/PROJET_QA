package com.project.controller;

import com.project.entity.PlantesMedicinales;
import com.project.service.PlantesMedicinalesServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PlantesMedicinalesControllerTest {

    @Mock
    private PlantesMedicinalesServiceImpl plantesMedicinalesService;

    @InjectMocks
    private PlantesMedicinalesController plantesMedicinalesController;

    private PlantesMedicinales plante;

    @BeforeEach
    public void setUp() {
        plante = new PlantesMedicinales();
        plante.setId(1L);
        plante.setNom("Mint");
        plante.setRegionGeo("Europe");
        plante.setProprietes("Anti-inflammatory");
        plante.setUtilisation("Tea");
    }

    @Test
    public void testCreatePlante() {
        when(plantesMedicinalesService.createPlante(any(PlantesMedicinales.class))).thenReturn(plante);

        ResponseEntity<PlantesMedicinales> response = plantesMedicinalesController.createPlante(plante);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(plante.getNom(), response.getBody().getNom());
        verify(plantesMedicinalesService, times(1)).createPlante(any(PlantesMedicinales.class));
    }

    @Test
    public void testUpdatePlante() {
        when(plantesMedicinalesService.updatePlante(eq(1L), any(PlantesMedicinales.class))).thenReturn(plante);

        ResponseEntity<PlantesMedicinales> response = plantesMedicinalesController.updatePlante(1L, plante);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(plante.getNom(), response.getBody().getNom());
        verify(plantesMedicinalesService, times(1)).updatePlante(eq(1L), any(PlantesMedicinales.class));
    }

    @Test
    public void testGetPlanteById() {
        when(plantesMedicinalesService.getPlanteById(1L)).thenReturn(plante);

        ResponseEntity<PlantesMedicinales> response = plantesMedicinalesController.getPlanteById(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(plante.getNom(), response.getBody().getNom());
        verify(plantesMedicinalesService, times(1)).getPlanteById(1L);
    }

    @Test
    public void testGetAllPlantes() {
        List<PlantesMedicinales> plantes = Arrays.asList(plante);
        when(plantesMedicinalesService.getAllPlantes()).thenReturn(plantes);

        ResponseEntity<List<PlantesMedicinales>> response = plantesMedicinalesController.getAllPlantes();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        verify(plantesMedicinalesService, times(1)).getAllPlantes();
    }

    @Test
    public void testDeletePlante() {
        doNothing().when(plantesMedicinalesService).deletePlante(1L);

        ResponseEntity<Void> response = plantesMedicinalesController.deletePlante(1L);

        assertEquals(204, response.getStatusCodeValue());
        verify(plantesMedicinalesService, times(1)).deletePlante(1L);
    }

    @Test
    public void testSearchPlantes() {
        List<PlantesMedicinales> plantes = Arrays.asList(plante);
        when(plantesMedicinalesService.searchPlantes("Mint", "Europe", "Anti-inflammatory", "Tea")).thenReturn(plantes);

        ResponseEntity<List<PlantesMedicinales>> response = plantesMedicinalesController.searchPlantes("Mint", "Europe", "Anti-inflammatory", "Tea");

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        verify(plantesMedicinalesService, times(1)).searchPlantes("Mint", "Europe", "Anti-inflammatory", "Tea");
    }

    @Test
    public void testRecommendPlantes() {
        List<PlantesMedicinales> recommendedPlantes = Arrays.asList(plante);
        when(plantesMedicinalesService.recommendPlantes("Anti-inflammatory", "Tea", "Precaution", "Interaction", "Proprietes")).thenReturn(recommendedPlantes);

        ResponseEntity<List<PlantesMedicinales>> response = plantesMedicinalesController.recommendPlantes("Anti-inflammatory", "Tea", "Precaution", "Interaction", "Proprietes");

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        verify(plantesMedicinalesService, times(1)).recommendPlantes("Anti-inflammatory", "Tea", "Precaution", "Interaction", "Proprietes");
    }
}
