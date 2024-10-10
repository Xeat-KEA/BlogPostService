package xeat.blogservice.childcategory.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChildCategoryCreateRequestDto {

    private Long blogId;

    private Long parentCategoryId;

    private String childName;
}
