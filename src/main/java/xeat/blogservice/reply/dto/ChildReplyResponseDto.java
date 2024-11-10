package xeat.blogservice.reply.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xeat.blogservice.reply.entity.Reply;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChildReplyResponseDto {
    private Long replyId;
    private String userId;
    private Long parentReplyId;
    private String mentionedUserId;
    private String content;
    private LocalDateTime createdDate;

    public static ChildReplyResponseDto toDto(Reply reply) {
        return new ChildReplyResponseDto(
                reply.getId(),
                reply.getUser().getUserId(),
                reply.getParentReplyId(),
                reply.getMentionedUser().getUserId(),
                reply.getContent(),
                reply.getCreatedDate()
        );
    }
}
