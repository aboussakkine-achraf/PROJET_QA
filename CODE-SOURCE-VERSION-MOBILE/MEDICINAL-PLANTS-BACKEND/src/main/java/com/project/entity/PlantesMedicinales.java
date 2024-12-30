package com.project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "plantes_medicinales")
@Data
public class PlantesMedicinales {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;              // Name

    @Column(columnDefinition = "TEXT")  // Updated to handle large text
    private String description;       // Description

    @Column(columnDefinition = "TEXT")  // Updated to handle large text
    private String proprietes;        // Properties

    @Column(columnDefinition = "TEXT")  // Updated to handle large text
    private String utilisation;       // Usage

    @Column(columnDefinition = "TEXT")  // Updated to handle large text
    private String precautions;       // Precautions

    @Column(columnDefinition = "TEXT")  // Updated to handle large text
    private String interactions;      // Interactions

    @Column(name = "principale_image")
    private String principaleImage;   // Main Image

    @Column(name = "image1")
    private String image1;            // Image 1

    @Column(name = "image2")
    private String image2;            // Image 2

    @Column(name = "image3")
    private String image3;            // Image 3

    @Column(name = "video_link")
    private String videoLink;         // Video Link

    @Column(name = "article_link")
    private String articleLink;       // Article Link

    @Column(name = "region_geo")
    private String regionGeo;         // Geographic Region

    @OneToMany(mappedBy = "plant", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore // Prevent serialization of comments to avoid infinite recursion
    private List<Comment> comments; // Relationship with comments for the plant
}
