package xeat.blogservice.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BlogIdResponseDto {

    private Long blogId;

    public static BlogIdResponseDto toDto(Long blogId) {
        return new BlogIdResponseDto(
                blogId
        );
    }
}
