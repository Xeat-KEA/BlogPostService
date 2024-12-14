package xeat.blogservice.blog.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "블로그 소개글 수정 요청 DTO")
public class BlogEditRequestDto {

    @Schema(description = "블로그 소개글", example = "블로그 소개글 1")
    private String mainContent;

    @Schema(description = "기존 블로그 소개글 이미지들(이미지가 여러개 있을 수도 있으니 list에 담아서 보내주세요)", example = "f8059ba1-f1a4-4c7a-aeea-b8158673dd93_image.jpg")
    private List<String> originalImageList;
}