package xeat.blogservice.reply.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xeat.blogservice.global.feignclient.UserInfoResponseDto;
import xeat.blogservice.reply.entity.Reply;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleReplyResponseDto {

    private Long replyId;
    private Long blogId;
    private String userName;
    private String profileUrl;
    private String content;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime createdDate;

    private List<ChildReplyResponseDto> childReplies;

    public static ArticleReplyResponseDto toDto(Reply reply, UserInfoResponseDto userInfo, List<ChildReplyResponseDto> childList) {
        return new ArticleReplyResponseDto(
                reply.getId(),
                reply.getUser().getId(),
                userInfo.getNickName(),
                userInfo.getProfileUrl(),
                reply.getContent(),
                reply.getCreatedDate(),
                childList
        );
    }
}