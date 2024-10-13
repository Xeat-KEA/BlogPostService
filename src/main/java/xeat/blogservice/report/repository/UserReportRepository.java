package xeat.blogservice.report.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xeat.blogservice.report.entity.UserReport;

public interface UserReportRepository extends JpaRepository<UserReport, Long> {
}
