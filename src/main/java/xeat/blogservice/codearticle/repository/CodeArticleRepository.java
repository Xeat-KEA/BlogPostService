package xeat.blogservice.codearticle.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import xeat.blogservice.codearticle.entity.CodeArticle;
import java.util.Optional;

public interface CodeArticleRepository extends JpaRepository<CodeArticle, Long> {

    boolean existsByArticleId(Long articleId);

    Optional<CodeArticle> findByArticleId(Long articleId);

    @Query("SELECT a FROM CodeArticle a WHERE a.article.isSecret = false AND a.article.isBlind = false ORDER BY a.createdDate DESC")
    Page<CodeArticle> findCodeArticleRecent(Pageable pageable);
}
