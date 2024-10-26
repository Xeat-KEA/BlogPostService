package xeat.blogservice.reply.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xeat.blogservice.reply.entity.Reply;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReplyResponseDto {

    private Long replyId;

    private Long articleId;

    private Long userId;

    private Long mentionedUserId;

    private Long parentReplyId;

    private String content;

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
