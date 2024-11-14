package xeat.blogservice.reply.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xeat.blogservice.reply.entity.Reply;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleReplyResponseDto {

    private Long replyId;
    private String userName;
    private String content;
    private LocalDateTime createdDate;
    private List<ChildReplyResponseDto> childReplies;

    public static ArticleReplyResponseDto toDto(Reply reply, String userName, List<ChildReplyResponseDto> childList) {
        return new ArticleReplyResponseDto(
                reply.getId(),
                userName,
                reply.getContent(),
                reply.getCreatedDate(),
                childList
        );
    }
}