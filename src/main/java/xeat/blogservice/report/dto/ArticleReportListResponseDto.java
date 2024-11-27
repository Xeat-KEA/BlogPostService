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
public class ArticleReportListResponseDto implements ResponseDto {

    private Long userReportId;

    private Long articleId;

    private String reporterName;

    private String title;

    private ReportCategory reportCategory;

    private String directCategory;

    private LocalDateTime createdDate;

    public static ArticleReportListResponseDto toDto(UserReport userReport, String reporterName) {
        return new ArticleReportListResponseDto(
                userReport.getId(),
                userReport.getArticle().getId(),
                reporterName,
                userReport.getArticle().getTitle(),
                userReport.getReportCategory(),
                userReport.getDirectCategory(),
                userReport.getCreatedDate()
        );
    }
}
