package com.project.Plantes.Medicinales.service;

import com.project.Plantes.Medicinales.entities.Article;
import com.project.Plantes.Medicinales.entities.Plante;
import com.project.Plantes.Medicinales.repository.ArticleRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleService {

    private final String uploadDir = "target/classes/static/article_images/";


    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public Article createArticle(Article article, MultipartFile imageFile) throws IOException {
        String imagePath = saveImage(imageFile);
        article.setImage(imagePath);
        return articleRepository.save(article);
    }

    /*
    public Article createArticle(Article article) {
        return articleRepository.save(article);
    }

     */

    public List<Article> getAllArticles() {
        List<Article> articles = new ArrayList<>();
        articleRepository.findAll().forEach(articles::add);
        return articles;
    }

    public void deleteArticle(Integer id) {
        articleRepository.deleteById(id);
    }

    public String saveImage(MultipartFile imageFile) throws IOException {
        if (imageFile == null || imageFile.isEmpty()) {
            return null;
        }
        Files.createDirectories(Paths.get(uploadDir)); //garantit que repertoire cible existe avant d'essayer de sauvegarder l'image
        String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename(); //  fileName le nom du ficihier avec un timestamp par exemple 1679512345678_nomImage.jpg.
        Path filePath = Paths.get(uploadDir + fileName); // concatenation du chemin et le nom du fichier pour obtenir le chemin complet src/main/resources/static/images/1679512345678_nomImage.jpg
        Files.write(filePath, imageFile.getBytes()); // pour sauvegarder un fichier
        return "/article_images/" + fileName; // les images sont accessible via ..../images/
    }


}
