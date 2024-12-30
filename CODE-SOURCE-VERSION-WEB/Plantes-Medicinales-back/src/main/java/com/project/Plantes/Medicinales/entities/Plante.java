package com.project.Plantes.Medicinales.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Plante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String image; // Path to the image
    private String description;
    private String proprietes;
    private String utilisation;
    private String preaucaution;

    @OneToMany(mappedBy = "plante", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Commentaire> commentaires; // List of comments

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    @JsonIgnore
    private User user; // Associated user


}
