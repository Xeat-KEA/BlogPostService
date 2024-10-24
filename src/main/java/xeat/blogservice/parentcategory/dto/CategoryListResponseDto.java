package xeat.blogservice.parentcategory.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xeat.blogservice.childcategory.dto.ChildCategoryResponseDto;
import xeat.blogservice.parentcategory.entity.ParentCategory;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryListResponseDto {

    private Long parentCategoryId;

    private Long blogId;

    private String parentName;

    private LocalDateTime createdDate;

    private List<ChildCategoryResponseDto> childCategories;

    public static CategoryListResponseDto toDto(ParentCategory parentCategory, List<ChildCategoryResponseDto> childCategories) {
        return new CategoryListResponseDto(
                parentCategory.getId(),
                parentCategory.getBlog().getId(),
                parentCategory.getParentName(),
                parentCategory.getCreatedDate(),
                childCategories
        );
    }
}
