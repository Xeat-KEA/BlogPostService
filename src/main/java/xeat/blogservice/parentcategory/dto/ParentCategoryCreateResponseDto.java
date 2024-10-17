package xeat.blogservice.parentcategory.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xeat.blogservice.childcategory.dto.ChildCategoryCreateResponseDto;
import xeat.blogservice.childcategory.entity.ChildCategory;
import xeat.blogservice.parentcategory.entity.ParentCategory;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ParentCategoryCreateResponseDto {

    private Long parentCategoryId;

    private Long blogId;

    private String parentName;

    private LocalDateTime createdAt;

    public static ParentCategoryCreateResponseDto toDto(ParentCategory parentCategory) {
        return new ParentCategoryCreateResponseDto(
                parentCategory.getId(),
                parentCategory.getBlog().getId(),
                parentCategory.getParentName(),
                parentCategory.getCreatedDate()
        );
    }
}
