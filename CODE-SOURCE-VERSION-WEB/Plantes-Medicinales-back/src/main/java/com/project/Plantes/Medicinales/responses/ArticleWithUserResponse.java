package com.project.Plantes.Medicinales.responses;

import com.project.Plantes.Medicinales.entities.Article;
import com.project.Plantes.Medicinales.entities.User;

public class ArticleWithUserResponse {
    private Article article;
    private User user;

    public ArticleWithUserResponse(Article article, User user) {
        this.article = article;
        this.user = user;
    }

    // Getters et setters
    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
