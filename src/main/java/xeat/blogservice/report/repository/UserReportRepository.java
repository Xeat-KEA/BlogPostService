package xeat.blogservice.report.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import xeat.blogservice.codearticle.entity.CodeArticle;
import xeat.blogservice.report.entity.UserReport;

import java.util.List;

public interface UserReportRepository extends JpaRepository<UserReport, Long> {

    Page<UserReport> findByArticleIsNotNullOrderByCreatedTimeDesc(Pageable pageable);
    Page<UserReport> findByReplyIsNotNullOrderByCreatedTimeDesc(Pageable pageable);
    Page<UserReport> findByBlogIsNotNullOrderByCreatedTimeDesc(Pageable pageable);
}
