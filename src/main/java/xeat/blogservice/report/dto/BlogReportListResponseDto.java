package xeat.blogservice.report.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xeat.blogservice.global.ResponseDto;
import xeat.blogservice.report.entity.ReportCategory;
import xeat.blogservice.report.entity.UserReport;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BlogReportListResponseDto implements ResponseDto {

    private Long userReportId;

    private Long blogId;

    private String reporterName;

    private String reportedUserName;

    private ReportCategory reportCategory;

    private String directCategory;

    private LocalDateTime createdDate;

    public static BlogReportListResponseDto toDto(UserReport userReport,
                                                  String reporterName, String reportedUserName) {
        return new BlogReportListResponseDto(
                userReport.getId(),
                userReport.getBlog().getId(),
                reporterName,
                reportedUserName,
                userReport.getReportCategory(),
                userReport.getDirectCategory(),
                userReport.getCreatedDate()
        );
    }
}
