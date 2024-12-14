package xeat.blogservice.parentcategory.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.aspectj.weaver.tools.ISupportsMessageContext;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "상위 게시판 이름 수정 요청 DTO")
public class ParentCategoryEditRequestDto {

    @Schema(description = "상위 게시판 수정 이름", example = "상위 게시판 수정 이름 1")
    private String parentName;
}
