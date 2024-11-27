package xeat.blogservice.notice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xeat.blogservice.notice.entity.Notice;
import xeat.blogservice.notice.entity.NoticeCategory;
import xeat.blogservice.report.entity.ReportCategory;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NoticeAdminSaveResponseDto {

    private Long noticeId;

    private Long blogId;

    private String sentUserName;

    private NoticeCategory noticeCategory;

    private ReportCategory reasonCategory;

    private String content;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime createdDate;

    public static NoticeAdminSaveResponseDto toDto(Notice notice) {
        return new NoticeAdminSaveResponseDto(
                notice.getId(),
                notice.getBlog().getId(),
                "관리자",
                notice.getNoticeCategory(),
                notice.getReasonCategory(),
                notice.getContent(),
                notice.getCreatedDate()
        );
    }
}
