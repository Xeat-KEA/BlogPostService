package xeat.blogservice.reply.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReplyPostRequestDto {

    private Long articleId;

    private Long userId;

    private Long parentReplyId;

    private Long mentionedUserId;

    private String content;


}
