package xeat.blogservice.childcategory.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xeat.blogservice.childcategory.entity.ChildCategory;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChildCategoryResponseDto {

    private Long childCategoryId;

    private Long parentCategoryId;

    private String childName;

    private LocalDateTime createdAt;

    public static ChildCategoryResponseDto toDto(ChildCategory childCategory) {
        return new ChildCategoryResponseDto(
                childCategory.getId(),
                childCategory.getParentCategory().getId(),
                childCategory.getChildName(),
                childCategory.getCreatedDate()
        );
    }
}