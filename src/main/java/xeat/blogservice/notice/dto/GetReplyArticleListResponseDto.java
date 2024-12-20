package xeat.blogservice.notice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xeat.blogservice.global.response.ResponseDto;
import xeat.blogservice.notice.entity.Notice;
import xeat.blogservice.notice.entity.NoticeCategory;
import xeat.blogservice.report.entity.ReportCategory;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetReplyArticleListResponseDto implements ResponseDto {

    @Schema(description = "알림 고유 ID", example = "1")
    private Long noticeId;

    @Schema(description = "알림 목록 조회 대상 게시글 고유 ID", example = "1")
    private Long repliedArticleId;

    @Schema(description = "댓글 고유 ID", example = "1")
    private Long replyId;

    @Schema(description = "알림을 보낸 해당 사용자 고유 ID", example = "1")
    private String sentUserNickName;

    @Schema(description = "알림 카테고리", example = "댓글 알림")
    private NoticeCategory noticeCategory;

    @Schema(description = "사유 카테고리", example = "삭제 사유")
    private ReportCategory reasonCategory;

    @Schema(description = "직접 입력일 시 ", example = "직접 입력한 사유")
    private String directCategory;

    @Schema(description = "해당 알림 본문 내용", example = "댓글 내용 1")
    private String content;

    @Schema(description = "알림 생성 시간", example = "2024-10-23T11:50:57.097171")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;

    public static GetReplyArticleListResponseDto toDto(Notice notice, String nickName) {
        return new GetReplyArticleListResponseDto(
                notice.getId(),
                notice.getArticle().getId(),
                notice.getReply().getId(),
                nickName,
                notice.getNoticeCategory(),
                notice.getReasonCategory(),
                notice.getDirectCategory(),
                notice.getContent(),
                notice.getCreatedDate()
        );
    }
}
