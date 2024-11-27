package xeat.blogservice.recommend.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import xeat.blogservice.article.entity.Article;
import xeat.blogservice.blog.entity.Blog;
import xeat.blogservice.recommend.entity.Recommend;

import java.util.Optional;

public interface RecommendRepository extends JpaRepository<Recommend, Long> {

    Optional<Recommend> findByArticleAndUser(Article article, Blog user);

    Boolean existsByArticleAndUser(Article article, Blog user);
}
