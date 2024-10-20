package xeat.blogservice.childcategory.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xeat.blogservice.childcategory.entity.ChildCategory;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChildCategoryCreateResponseDto {

    private Long childCategoryId;

    private Long blogId;

    private Long parentCategoryId;

    private String childName;

    private LocalDateTime createdAt;

    public static ChildCategoryCreateResponseDto toDto(ChildCategory childCategory) {
        return new ChildCategoryCreateResponseDto(
                childCategory.getId(),
                childCategory.getBlog().getId(),
                childCategory.getParentCategory().getId(),
                childCategory.getChildName(),
                childCategory.getCreatedDate()
        );
    }
}
