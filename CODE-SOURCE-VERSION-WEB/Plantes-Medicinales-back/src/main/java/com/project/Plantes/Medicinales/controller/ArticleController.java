package com.project.Plantes.Medicinales.controller;

import com.project.Plantes.Medicinales.entities.Article;
import com.project.Plantes.Medicinales.entities.User;
import com.project.Plantes.Medicinales.repository.ArticleRepository;
import com.project.Plantes.Medicinales.repository.UserRepository;
import com.project.Plantes.Medicinales.responses.ArticleFlatResponse;
import com.project.Plantes.Medicinales.responses.ArticleWithUserResponse;
import com.project.Plantes.Medicinales.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {


    @Autowired
    private ArticleService articleService ;

    @Autowired
    private UserRepository userRepository ;

    @Autowired
    private ArticleRepository articleRepository ;

    /*
    @PostMapping
    public ResponseEntity<Article> createArticle(@RequestBody Article article) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        article.setUser(currentUser);

        Article savedArticle = articleService.createArticle(article);
        return ResponseEntity.ok(savedArticle);
    }

     */

    @PostMapping("/ajouter")
    public ResponseEntity<ArticleWithUserResponse> createArticle(
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam(value = "image", required = false) MultipartFile imageFile
    ) throws IOException {

        Article article = new Article();
        article.setTitle(title);
        article.setContent(content);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();

        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        article.setUser(currentUser);

        if (imageFile != null && !imageFile.isEmpty()) {
            String imagePath = articleService.saveImage(imageFile);
            article.setImage(imagePath);
        }

        Article savedArticle = articleService.createArticle(article, imageFile);

        ArticleWithUserResponse response = new ArticleWithUserResponse(savedArticle, currentUser);
        return ResponseEntity.ok(response);
    }





    //methode de creerArticle sans image
    /*
    @PostMapping
    public ResponseEntity<ArticleWithUserResponse> createArticlee(@RequestBody Article article) {

        //Récupération de l'utilisateur authentifié
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();


        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));


        article.setUser(currentUser);


        Article savedArticle = articleService.createArticle(article);


        ArticleWithUserResponse response = new ArticleWithUserResponse(savedArticle, currentUser);

        return ResponseEntity.ok(response);
    }

     */



  /*
    @GetMapping
    public ResponseEntity<List<ArticleWithUserResponse>> getAllArticles() {

        List<Article> articles = articleService.getAllArticles();


        List<ArticleWithUserResponse> response = articles.stream()
                .map(article -> new ArticleWithUserResponse(article, article.getUser()))
                .toList();

        return ResponseEntity.ok(response);
    }

   */
  @GetMapping
  public ResponseEntity<List<ArticleFlatResponse>> getAllArticles() {
      List<Article> articles = articleService.getAllArticles();

      // Convertir chaque article en ArticleFlatResponse
      List<ArticleFlatResponse> response = articles.stream()
              .map(ArticleFlatResponse::new)
              .toList();

      return ResponseEntity.ok(response);
  }





    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Integer id) {
        articleService.deleteArticle(id);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/deleteAll")
    public ResponseEntity<String> deleteAll() {
        articleRepository.deleteAll();  // Supprime tous les articles
        return ResponseEntity.ok("Tous les articles ont été supprimés avec succès.");
    }
}
