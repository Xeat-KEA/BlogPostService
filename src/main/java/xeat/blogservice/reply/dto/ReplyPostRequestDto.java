package xeat.blogservice.reply.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "댓글 작성 요청 DTO")
public class ReplyPostRequestDto {

    @Schema(description = "댓글 작성한 해당 게시글 고유 ID", example = "1")
    private Long articleId;

    @Schema(description = "댓글 작성한 사용자 고유 ID", example = "1")
    private String userId;

    @Schema(description = "상위 댓글 고유 ID", example = "1")
    private Long parentReplyId;

    @Schema(description = "언급된 사용자 고유 ID", example = "1")
    private String mentionedUserId;

    @Schema(description = "댓글 내용", example = "댓글 내용 1")
    private String content;
}
