package xeat.blogservice.parentcategory.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryTotalResponseDto {

    private String userName;

    private List<CategoryListResponseDto> categoryList;

    public static CategoryTotalResponseDto toDto(String userName, List<CategoryListResponseDto> categoryList) {
        return new CategoryTotalResponseDto(userName, categoryList);
    }
}
