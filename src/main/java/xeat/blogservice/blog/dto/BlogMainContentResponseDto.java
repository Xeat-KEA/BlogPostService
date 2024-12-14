package xeat.blogservice.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xeat.blogservice.blog.entity.Blog;

import java.util.Base64;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BlogMainContentResponseDto {

    private Long blogId;

    private String mainContent;

    public static BlogMainContentResponseDto toDto(Blog blog, String mainContent) {
        return new BlogMainContentResponseDto(
                blog.getId(),
                mainContent
        );
    }
}
