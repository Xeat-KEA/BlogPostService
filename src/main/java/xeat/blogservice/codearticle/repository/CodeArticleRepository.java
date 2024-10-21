package xeat.blogservice.codearticle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xeat.blogservice.codearticle.entity.CodeArticle;
import java.util.Optional;

public interface CodeArticleRepository extends JpaRepository<CodeArticle, Long> {

    boolean existsByArticleId(Long articleId);

    Optional<CodeArticle> findByArticleId(Long articleId);
}
