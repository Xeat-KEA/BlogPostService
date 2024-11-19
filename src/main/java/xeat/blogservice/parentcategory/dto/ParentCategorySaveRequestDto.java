package xeat.blogservice.parentcategory.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "상위 게시판 생성 요청 DTO")
public class ParentCategorySaveRequestDto {

    @Schema(description = "상위 게시판 생성 할 블로그 고유 ID", example = "1")
    private Long blogId;

    @Schema(description = "상위 게시판 이름", example = "상위 게시판 이름 1")
    private String parentName;
}
