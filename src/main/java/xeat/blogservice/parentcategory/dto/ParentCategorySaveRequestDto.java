package xeat.blogservice.parentcategory.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ParentCategorySaveRequestDto {

    private Long blogId;

    private String parentName;
}
