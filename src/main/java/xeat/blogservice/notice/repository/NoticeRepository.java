package xeat.blogservice.notice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import xeat.blogservice.notice.entity.Notice;
import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

    @Query("SELECT n FROM Notice n WHERE n.blog.Id = :blogId ORDER BY n.createdDate DESC")
    Page<Notice> findNoticeList(@Param("blogId") Pageable pageable, Long blogId);
}
