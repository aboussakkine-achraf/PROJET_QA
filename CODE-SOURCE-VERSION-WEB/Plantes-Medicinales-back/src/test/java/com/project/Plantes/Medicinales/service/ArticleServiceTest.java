package com.project.Plantes.Medicinales.service;

import com.project.Plantes.Medicinales.entities.Article;
import com.project.Plantes.Medicinales.repository.ArticleRepository;
import com.project.Plantes.Medicinales.service.ArticleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ArticleServiceTest {

    @Mock
    private ArticleRepository articleRepository;

    @InjectMocks
    private ArticleService articleService;

    @BeforeEach
    void setUp() {
        articleService = new ArticleService(articleRepository);
    }

    @Test
    void createArticle_shouldSaveArticleWithImage() throws IOException {
        // Arrange
        String title = "Test Article";
        String content = "Test content";
        MockMultipartFile imageFile = new MockMultipartFile("image", "test.jpg", "image/jpeg", "test image".getBytes());

        Article article = new Article();
        article.setTitle(title);
        article.setContent(content);

        when(articleRepository.save(any(Article.class))).thenReturn(article);

        // Act
        Article savedArticle = articleService.createArticle(article, imageFile);

        // Assert
        assertEquals(title, savedArticle.getTitle());
        assertEquals(content, savedArticle.getContent());
        verify(articleRepository, times(1)).save(any(Article.class));  // Ensure save was called once
    }

    @Test
    void saveImage_shouldReturnCorrectImagePath() throws IOException {
        // Arrange
        MockMultipartFile imageFile = new MockMultipartFile("image", "test.jpg", "image/jpeg", "test image".getBytes());

        // Act
        String imagePath = articleService.saveImage(imageFile);

        // Assert
        assert imagePath != null && imagePath.contains("/article_images/");
    }
}

