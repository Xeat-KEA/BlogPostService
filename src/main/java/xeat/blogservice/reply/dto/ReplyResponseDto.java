package xeat.blogservice.reply.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    private String userName;

    @Schema(description = "언급된 사용자 고유 ID", example = "1")
    private String mentionedUserName;

    @Schema(description = "상위 댓글 고유 ID", example = "1")
    private Long parentReplyId;

    @Schema(description = "댓글 내용", example = "댓글 내용 1")
    private String content;

    @Schema(description = "댓글 생성 시간", example = "2024-10-23T11:50:57.097171")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;

    public static ReplyResponseDto parentReplyDto(Reply reply, String userName) {
        return new ReplyResponseDto(
                reply.getId(),
                reply.getArticle().getId(),
                userName,
                null,
                reply.getParentReplyId(),
                reply.getContent(),
                reply.getCreatedDate()
        );
    }

    public static ReplyResponseDto childReplyDto(Reply reply, String userName, String mentionedUserName) {
        return new ReplyResponseDto(
                reply.getId(),
                reply.getArticle().getId(),
                userName,
                mentionedUserName,
                reply.getParentReplyId(),
                reply.getContent(),
                reply.getCreatedDate()
        );
    }
}
