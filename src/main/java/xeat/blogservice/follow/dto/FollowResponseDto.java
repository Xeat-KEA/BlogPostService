package xeat.blogservice.follow.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xeat.blogservice.blog.entity.Blog;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "팔로우 요청 응답 DTO")
public class FollowResponseDto {

    @Schema(description = "팔로우 요청 받은 해당 블로그의 고유 ID", example = "1")
    private Long blogId;

    @Schema(description = "팔로우 요청 받은 해당 블로그의 팔로우 수", example = "5(팔로우 요청 받은 해당 블로그의 팔로우 수)")
    private Integer followCount;

    public static FollowResponseDto toDto(Blog blog) {

        return new FollowResponseDto(
                blog.getId(),
                blog.getFollowCount());
    }
}
