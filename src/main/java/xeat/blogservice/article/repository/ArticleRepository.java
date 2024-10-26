package xeat.blogservice.article.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import xeat.blogservice.article.entity.Article;


public interface ArticleRepository extends JpaRepository<Article, Long> {

    @Query("SELECT a from Article a WHERE a.isSecret = false AND a.isBlind = false ORDER BY a.createdDate DESC")
    Page<Article> findAllArticleRecent(Pageable pageable);

    @Query("SELECT a from Article a WHERE a.isSecret = false AND a.isBlind = false AND a.childCategory.id IS NOT NULL ORDER BY a.createdDate DESC")
    Page<Article> findArticleRecent(Pageable pageable);

    @Query("SELECT a from Article a ORDER BY a.likeCount DESC")
    Page<Article> findArticleLikeCount(Pageable pageable);
}
