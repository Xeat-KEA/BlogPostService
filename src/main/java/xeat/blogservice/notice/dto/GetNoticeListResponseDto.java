package xeat.blogservice.notice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xeat.blogservice.global.ResponseDto;
import xeat.blogservice.notice.entity.Notice;
import xeat.blogservice.notice.entity.NoticeCategory;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "알림 목록 조회 응답 DTO")
public class GetNoticeListResponseDto implements ResponseDto {

    @Schema(description = "알림 고유 ID", example = "1")
    private Long noticeId;

    @Schema(description = "알림 목록 조회 대상 블로그 고유 ID", example = "1")
    private Long blogId;

    @Schema(description = "알림을 보낸 해당 사용자 고유 ID", example = "1")
    private String sentUserNickName;

    @Schema(description = "알림 카테고리", example = "댓글 알림")
    private NoticeCategory noticeCategory;

    @Schema(description = "해당 알림 본문 내용", example = "댓글 내용 1")
    private String content;

    @Schema(description = "알림 생성 시간", example = "2024-10-23T11:50:57.097171")
    private LocalDateTime createdDate;

    public static GetNoticeListResponseDto toDto(Notice notice, String nickName) {
        return new GetNoticeListResponseDto(
                notice.getId(),
                notice.getBlog().getId(),
                nickName,
                notice.getNoticeCategory(),
                notice.getContent(),
                notice.getCreatedDate()
        );
    }
}
