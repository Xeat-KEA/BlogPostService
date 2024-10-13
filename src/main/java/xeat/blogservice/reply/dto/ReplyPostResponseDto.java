package xeat.blogservice.reply.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xeat.blogservice.reply.entity.Reply;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReplyPostResponseDto {

    private Long replyId;

    private Long articleId;

    private Long userId;

    private Long parentReplyId;

    private String content;

    public static ReplyPostResponseDto toDto(Reply reply) {
        return new ReplyPostResponseDto(
                reply.getId(),
                reply.getArticle().getId(),
                reply.getUser().getUserId(),
                reply.getParentReplyId(),
                reply.getContent()
        );
    }
}
