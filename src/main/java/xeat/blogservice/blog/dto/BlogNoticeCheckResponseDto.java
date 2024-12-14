package xeat.blogservice.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xeat.blogservice.blog.entity.Blog;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BlogNoticeCheckResponseDto {

    private Long blogId;

    private Boolean noticeCheck;

    public static BlogNoticeCheckResponseDto toDto(Blog blog) {
        return new BlogNoticeCheckResponseDto(
                blog.getId(),
                blog.getNoticeCheck()
        );
    }
}
