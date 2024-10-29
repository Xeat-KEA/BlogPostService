package xeat.blogservice.reply.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xeat.blogservice.reply.entity.Reply;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "댓글 작성 응답 DTO")
public class ReplyResponseDto {

    @Schema(description = "댓글 고유 ID", example = "1")
    private Long replyId;

    @Schema(description = "댓글 작성한 해당 게시글 고유 ID", example = "1")
    private Long articleId;

    @Schema(description = "댓글 작성한 사용자 고유 ID", example = "1")
    private Long userId;

    @Schema(description = "언급된 사용자 고유 ID", example = "1")
    private Long mentionedUserId;

    @Schema(description = "상위 댓글 고유 ID", example = "1")
    private Long parentReplyId;

    @Schema(description = "댓글 내용", example = "댓글 내용 1")
    private String content;

    @Schema(description = "댓글 생성 시간", example = "2024-10-23T11:50:57.097171")
    private LocalDateTime createdDate;

    public static ReplyResponseDto toDto(Reply reply) {
        if (reply.getMentionedUser() == null) {
            return new ReplyResponseDto(
                    reply.getId(),
                    reply.getArticle().getId(),
                    reply.getUser().getUserId(),
                    null,
                    reply.getParentReplyId(),
                    reply.getContent(),
                    reply.getCreatedDate()
            );
        }
        else {
            return new ReplyResponseDto(
                    reply.getId(),
                    reply.getArticle().getId(),
                    reply.getUser().getUserId(),
                    reply.getMentionedUser().getUserId(),
                    reply.getParentReplyId(),
                    reply.getContent(),
                    reply.getCreatedDate()
            );
        }
    }
}
