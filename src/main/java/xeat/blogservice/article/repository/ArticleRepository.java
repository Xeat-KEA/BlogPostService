package xeat.blogservice.article.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xeat.blogservice.article.entity.Article;

public interface ArticleRepository extends JpaRepository<Article, Long> {

}
