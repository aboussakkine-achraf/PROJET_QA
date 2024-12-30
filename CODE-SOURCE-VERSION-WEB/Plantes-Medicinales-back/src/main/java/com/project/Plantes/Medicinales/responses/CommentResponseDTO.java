package com.project.Plantes.Medicinales.responses;

import com.project.Plantes.Medicinales.entities.Commentaire;
import com.project.Plantes.Medicinales.entities.User;

public class CommentResponseDTO {
    private Long id ;
    private String content ;
    private String userFullName ;

    public CommentResponseDTO(Commentaire commentaire) {
        this.id = commentaire.getId();
        this.content = commentaire.getContent();
      //  this.userName = userName;
        this.userFullName = commentaire.getUser() != null ? commentaire.getUser().getFullName() : "Utilisateur inconnu";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }
}

