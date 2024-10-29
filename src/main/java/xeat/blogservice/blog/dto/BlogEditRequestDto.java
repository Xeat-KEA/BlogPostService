package xeat.blogservice.blog.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "블로그 소개글 수정 요청 DTO")
public class BlogEditRequestDto {

    @Schema(description = "블로그 소개글", example = "블로그 소개글 1")
    private String mainContent;
}