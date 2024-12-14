package xeat.blogservice.report.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import xeat.blogservice.codearticle.entity.CodeArticle;
import xeat.blogservice.report.entity.UserReport;

import java.util.List;

public interface UserReportRepository extends JpaRepository<UserReport, Long> {

    Page<UserReport> findByArticleIsNotNullOrderByCreatedDateDesc(Pageable pageable);
    Page<UserReport> findByReplyIsNotNullOrderByCreatedDateDesc(Pageable pageable);
    Page<UserReport> findByBlogIsNotNullOrderByCreatedDateDesc(Pageable pageable);

    @Query("DELETE FROM UserReport ur WHERE ur.blog.id = :blogId")
    void deleteAllByBlogId(@Param("blogId") Long blogId);
}
