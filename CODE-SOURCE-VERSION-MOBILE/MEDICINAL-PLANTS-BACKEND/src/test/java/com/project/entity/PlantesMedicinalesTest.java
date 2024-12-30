package com.project.entity;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlantesMedicinalesTest {

    @Test
    void testDefaultConstructorAndSetters() {
        // Arrange
        PlantesMedicinales plant = new PlantesMedicinales();

        // Act
        plant.setId(1L);
        plant.setNom("Aloe Vera");
        plant.setDescription("A succulent plant species of the genus Aloe.");
        plant.setProprietes("Healing and moisturizing properties.");
        plant.setUtilisation("Used for skin treatments.");
        plant.setPrecautions("May cause skin irritation in sensitive individuals.");
        plant.setInteractions("Can interact with anticoagulant drugs.");
        plant.setPrincipaleImage("image1.jpg");
        plant.setImage1("image2.jpg");
        plant.setImage2("image3.jpg");
        plant.setImage3("image4.jpg");
        plant.setVideoLink("https://example.com/video");
        plant.setArticleLink("https://example.com/article");
        plant.setRegionGeo("Tropical regions");
        List<Comment> comments = new ArrayList<>();
        plant.setComments(comments);

        // Assert
        assertEquals(1L, plant.getId());
        assertEquals("Aloe Vera", plant.getNom());
        assertEquals("A succulent plant species of the genus Aloe.", plant.getDescription());
        assertEquals("Healing and moisturizing properties.", plant.getProprietes());
        assertEquals("Used for skin treatments.", plant.getUtilisation());
        assertEquals("May cause skin irritation in sensitive individuals.", plant.getPrecautions());
        assertEquals("Can interact with anticoagulant drugs.", plant.getInteractions());
        assertEquals("image1.jpg", plant.getPrincipaleImage());
        assertEquals("image2.jpg", plant.getImage1());
        assertEquals("image3.jpg", plant.getImage2());
        assertEquals("image4.jpg", plant.getImage3());
        assertEquals("https://example.com/video", plant.getVideoLink());
        assertEquals("https://example.com/article", plant.getArticleLink());
        assertEquals("Tropical regions", plant.getRegionGeo());
        assertEquals(comments, plant.getComments());
    }

    @Test
    void testAllArgsConstructor() {
        // Arrange
        List<Comment> comments = new ArrayList<>();
        PlantesMedicinales plant = new PlantesMedicinales(
                1L,
                "Aloe Vera",
                "A succulent plant species of the genus Aloe.",
                "Healing and moisturizing properties.",
                "Used for skin treatments.",
                "May cause skin irritation in sensitive individuals.",
                "Can interact with anticoagulant drugs.",
                "image1.jpg",
                "image2.jpg",
                "image3.jpg",
                "image4.jpg",
                "https://example.com/video",
                "https://example.com/article",
                "Tropical regions",
                comments
        );

        // Assert
        assertNotNull(plant);
        assertEquals(1L, plant.getId());
        assertEquals(comments, plant.getComments());
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        PlantesMedicinales plant1 = new PlantesMedicinales();
        plant1.setId(1L);

        PlantesMedicinales plant2 = new PlantesMedicinales();
        plant2.setId(1L);

        // Assert
        assertEquals(plant1, plant2);
        assertEquals(plant1.hashCode(), plant2.hashCode());
    }

    @Test
    void testToString() {
        // Arrange
        PlantesMedicinales plant = new PlantesMedicinales();
        plant.setId(1L);
        plant.setNom("Aloe Vera");

        // Act
        String result = plant.toString();

        // Assert
        assertTrue(result.contains("Aloe Vera"));
        assertTrue(result.contains("1"));
    }
}
