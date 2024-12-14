package xeat.blogservice.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xeat.blogservice.blog.entity.Blog;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BlogEditResponseDto {

    private Long blogId;

    private String mainContent;

    private List<String> originalImageList;

    public static BlogEditResponseDto toDto(Blog blog, String mainContent, List<String> originalImageList) {
        return new BlogEditResponseDto(
                blog.getId(),
                mainContent,
                originalImageList
        );
    }
}
