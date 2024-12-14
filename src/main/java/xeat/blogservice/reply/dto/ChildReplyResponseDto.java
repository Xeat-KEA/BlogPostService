package xeat.blogservice.reply.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xeat.blogservice.global.feignclient.UserInfoResponseDto;
import xeat.blogservice.reply.entity.Reply;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChildReplyResponseDto {
    private Long replyId;
    private Long blogId;
    private String userId;
    private String userName;
    private String profileUrl;
    private Long parentReplyId;
    private String mentionedUserName;
    private String content;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;

    public static ChildReplyResponseDto toDto(Reply reply, UserInfoResponseDto userInfo, String mentionedUserName) {
        return new ChildReplyResponseDto(
                reply.getId(),
                reply.getUser().getId(),
                reply.getUser().getUserId(),
                userInfo.getNickName(),
                userInfo.getProfileUrl(),
                reply.getParentReplyId(),
                mentionedUserName,
                reply.getContent(),
                reply.getCreatedDate()
        );
    }
}
