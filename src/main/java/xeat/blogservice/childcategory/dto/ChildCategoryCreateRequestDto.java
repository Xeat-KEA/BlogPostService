package xeat.blogservice.childcategory.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "하위 게시판 생성 요청 DTO")
public class ChildCategoryCreateRequestDto {

    @Schema(description = "하위 게시판 고유 ID", example = "1")
    private Long parentCategoryId;

    @Schema(description = "하위 게시판 이름", example = "하위 게시판1")
    private String childName;
}
