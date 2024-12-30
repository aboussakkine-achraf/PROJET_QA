package com.project.Plantes.Medicinales.repository;

import com.project.Plantes.Medicinales.entities.Article;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends CrudRepository<Article, Integer> {
}
