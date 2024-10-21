package xeat.blogservice.reply.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xeat.blogservice.reply.entity.Reply;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReplyPostResponseDto {

    private Long articleId;

    private Long userId;

    private Long mentionedUserId;

    private Long parentReplyId;


    private String content;

    public static ReplyPostResponseDto toDto(Reply reply, Long mentionedUserId) {
        return new ReplyPostResponseDto(
                reply.getArticle().getId(),
                reply.getUser().getUserId(),
                mentionedUserId,
                reply.getParentReplyId(),
                reply.getContent()
        );
    }
}
