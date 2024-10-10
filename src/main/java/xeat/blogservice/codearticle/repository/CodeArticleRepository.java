package xeat.blogservice.codearticle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xeat.blogservice.codearticle.entity.CodeArticle;

public interface CodeArticleRepository extends JpaRepository<CodeArticle, Long> {
}
