package com.project.Plantes.Medicinales.responses;

import com.project.Plantes.Medicinales.entities.Article;

public class ArticleFlatResponse {
    private Integer id;
    private String title;
    private String content;
    private String image;
    private String createdAt;
    private String updatedAt;
    private String userFullName;

    public ArticleFlatResponse(Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent();
        this.image = article.getImage();
        this.createdAt = article.getCreatedAt().toString();
        this.updatedAt = article.getUpdatedAt().toString();
        this.userFullName = article.getUser() != null ? article.getUser().getFullName() : "Utilisateur inconnu";
    }

    // Getters et Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }
}

