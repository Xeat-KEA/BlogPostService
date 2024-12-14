package xeat.blogservice.report.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xeat.blogservice.article.entity.Article;
import xeat.blogservice.report.entity.ReportCategory;
import xeat.blogservice.report.entity.UserReport;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleReportResponseDto {

    private Long articleId;

    private String reporterName;

    private ReportCategory reportCategory;

    private String directCategory;

    private Integer reportCount;

    private Boolean isBlind;

    public static ArticleReportResponseDto toDto(UserReport userReport, Article article, String reporterName) {
        return new ArticleReportResponseDto(
                userReport.getArticle().getId(),
                reporterName,
                userReport.getReportCategory(),
                userReport.getDirectCategory(),
                article.getReportCount(),
                article.getIsBlind()
        );
    }
}
