package com.project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comments") // Custom table name for clarity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content; // The text of the comment

    @Column(nullable = false)
    private LocalDateTime timestamp; // The time the comment was created

    @ManyToOne(fetch = FetchType.LAZY) // Many comments can belong to one plant
    @JoinColumn(name = "plant_id", nullable = false)
    @JsonIgnore // Prevent serialization of the `plant` to avoid infinite recursion
    private PlantesMedicinales plant;

    @ManyToOne(fetch = FetchType.LAZY) // Many comments can belong to one user
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore // Prevent serialization of the `plant` to avoid infinite recursion
    private User user;

    @PrePersist
    public void prePersist() {
        if (timestamp == null) {
            timestamp = LocalDateTime.now();  // Set timestamp to the current time before persisting
        }
    }
}
