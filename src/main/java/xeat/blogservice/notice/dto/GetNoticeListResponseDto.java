package xeat.blogservice.notice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xeat.blogservice.notice.entity.Notice;
import xeat.blogservice.notice.entity.NoticeCategory;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetNoticeListResponseDto {

    private Long noticeId;

    private Long blogId;

    private Long sentUserId;

    private NoticeCategory noticeCategory;

    private String content;

    private LocalDateTime createdDate;

    public static GetNoticeListResponseDto toDto(Notice notice) {
        return new GetNoticeListResponseDto(
                notice.getId(),
                notice.getBlog().getId(),
                notice.getSentUser().getUserId(),
                notice.getNoticeCategory(),
                notice.getContent(),
                notice.getCreatedDate()
        );
    }
}
