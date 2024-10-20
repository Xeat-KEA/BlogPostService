package xeat.blogservice.notice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xeat.blogservice.notice.entity.Notice;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
}
