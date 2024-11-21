package xeat.blogservice.reply.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xeat.blogservice.global.userclient.UserInfoResponseDto;
import xeat.blogservice.reply.entity.Reply;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleReplyResponseDto {

    private Long replyId;
    private String userName;
    private String profileUrl;
    private String content;
    private LocalDateTime createdDate;
    private List<ChildReplyResponseDto> childReplies;

    public static ArticleReplyResponseDto toDto(Reply reply, UserInfoResponseDto userInfo, List<ChildReplyResponseDto> childList) {
        return new ArticleReplyResponseDto(
                reply.getId(),
                userInfo.getNickName(),
                userInfo.getProfileUrl(),
                reply.getContent(),
                reply.getCreatedDate(),
                childList
        );
    }
}