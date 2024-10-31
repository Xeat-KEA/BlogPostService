package xeat.blogservice.notice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xeat.blogservice.blog.entity.Blog;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NoticeCheckResponseDto {

    private Long blogId;

    private Boolean noticeCheck;

    public static NoticeCheckResponseDto toDto(Blog blog) {
        return new NoticeCheckResponseDto(
                blog.getId(),
                blog.getNoticeCheck()
        );
    }
}
