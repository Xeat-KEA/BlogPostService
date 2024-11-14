package xeat.blogservice.report.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xeat.blogservice.report.entity.ReportCategory;
import xeat.blogservice.report.entity.UserReport;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReplyReportResponseDto {

    private Long replyId;

    private String reporterName;

    private ReportCategory reportCategory;

    private String directCategory;

    public static ReplyReportResponseDto toDto(UserReport userReport, String reporterName) {

        return new ReplyReportResponseDto(
                userReport.getReply().getId(),
                userReport.getBlog().getUserId(),
                userReport.getReportCategory(),
                userReport.getDirectCategory()
        );
    }
}
