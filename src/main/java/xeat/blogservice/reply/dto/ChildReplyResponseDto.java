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
    private String userName;
    private Long parentReplyId;
    private String mentionedUserName;
    private String content;
    private LocalDateTime createdDate;

    public static ChildReplyResponseDto toDto(Reply reply, String userName, String mentionedUserName) {
        return new ChildReplyResponseDto(
                reply.getId(),
                userName,
                reply.getParentReplyId(),
                mentionedUserName,
                reply.getContent(),
                reply.getCreatedDate()
        );
    }
}
