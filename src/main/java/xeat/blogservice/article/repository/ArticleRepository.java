package xeat.blogservice.article.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import xeat.blogservice.article.entity.Article;
import xeat.blogservice.codearticle.entity.Difficulty;


public interface ArticleRepository extends JpaRepository<Article, Long> {

    @Query("SELECT a from Article a WHERE a.blog.id = :blogId ORDER BY a.createdDate DESC")
    Page<Article> findAllArticleByBlogId(Pageable pageable, Long blogId);

    @Query("SELECT a from Article a WHERE a.blog.id =:blogId AND a.childCategory.id = :childCategoryId ORDER BY a.createdDate DESC")
    Page<Article> findArticleChildCategoryId(Pageable pageable, Long blogId, Long childCategoryId);

    @Query("SELECT a from Article a WHERE a.isSecret = false AND a.isBlind = false ORDER BY a.createdDate DESC")
    Page<Article> findAllArticleRecent(Pageable pageable);

    @Query("SELECT a from Article a WHERE a.isSecret = false AND a.isBlind = false AND a.childCategory.id > 5 ORDER BY a.createdDate DESC")
    Page<Article> findArticleRecent(Pageable pageable);

    @Query("SELECT a from Article a ORDER BY a.likeCount DESC")
    Page<Article> findArticleLikeCount(Pageable pageable);

    @Query("SELECT a FROM Article a WHERE a.blog.id = :blogId AND (a.title LIKE %:searchWord% OR a.content LIKE %:searchWord%)")
    Page<Article> findArticleListContaining(Pageable pageable, Long blogId, String searchWord);
}
