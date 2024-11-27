package xeat.blogservice.report.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xeat.blogservice.global.ResponseDto;
import xeat.blogservice.reply.entity.Reply;
import xeat.blogservice.report.entity.ReportCategory;
import xeat.blogservice.report.entity.UserReport;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReplyReportListResponseDto implements ResponseDto {

    private Long userReportId;

    private Long replyId;

    private String reporterName;

    private String content;

    private ReportCategory reportCategory;

    private String directCategory;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;

    public static ReplyReportListResponseDto toDto(UserReport userReport, String reporterName) {
        return new ReplyReportListResponseDto(
                userReport.getId(),
                userReport.getReply().getId(),
                reporterName,
                userReport.getReply().getContent(),
                userReport.getReportCategory(),
                userReport.getDirectCategory(),
                userReport.getCreatedDate()
        );
    }
}
