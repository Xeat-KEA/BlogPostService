package xeat.blogservice.notice.dto;

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
