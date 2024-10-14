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

    private ReportCategory reportCategory;

    private String directCategory;

    private Integer reportCount;

    private Boolean isBlind;

    public static ArticleReportResponseDto toDto(UserReport userReport, Article article) {
        return new ArticleReportResponseDto(
                userReport.getArticle().getId(),
                userReport.getReportCategory(),
                userReport.getDirectCategory(),
                article.getReportCount(),
                article.getIsBlind()
        );
    }
}
