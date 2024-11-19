package xeat.blogservice.blog.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "블로그 생성 요청 DTO")
public class BlogCreateRequestDto {

    @Schema(description = "게시글 생성을 요청한 사용자 고유 ID", example = "1")
    private Long userId;
}
