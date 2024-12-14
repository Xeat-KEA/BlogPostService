package xeat.blogservice.childcategory.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "하위 게시판 이름 수정 요청 DTO")
public class ChildCategoryEditRequestDto {

    @Schema(description = "하위 게시판 이름", example = "하위 게시판 수정 이름1")
    private String childName;
}

