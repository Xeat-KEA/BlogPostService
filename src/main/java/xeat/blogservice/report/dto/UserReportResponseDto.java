package xeat.blogservice.report.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xeat.blogservice.report.entity.UserReport;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserReportResponseDto {

    private Long userReportId;

    private Long articleId;

    private Long replyId;

    public static UserReportResponseDto toDto(UserReport userReport) {
        if (userReport.getReply() != null) {
            return new UserReportResponseDto(
                    userReport.getId(),
                    userReport.getReply().getArticle().getId(),
                    userReport.getReply().getId()
            );
        }
        else {
            return new UserReportResponseDto(
                    userReport.getId(),
                    userReport.getArticle().getId(),
                    null
            );
        }
    }
}
