package xeat.blogservice.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xeat.blogservice.blog.entity.Blog;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BlogMainContentResponseDto {

    private String mainContent;

    public static BlogMainContentResponseDto toDto(Blog blog) {
        return new BlogMainContentResponseDto(
                blog.getMainContent()
        );
    }
}