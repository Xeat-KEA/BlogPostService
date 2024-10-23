package xeat.blogservice.article.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import xeat.blogservice.article.entity.Article;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    @Query("SELECT a from Article a WHERE a.isSecret = false AND a.isBlind = false ORDER BY a.createdDate DESC")
    List<Article> findRecentArticle();
}
