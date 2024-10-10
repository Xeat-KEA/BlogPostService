package xeat.blogservice.parentcategory.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import xeat.blogservice.global.Response;
import xeat.blogservice.parentcategory.dto.ParentCategorySaveRequestDto;
import xeat.blogservice.parentcategory.entity.ParentCategory;
import xeat.blogservice.parentcategory.service.ParentCategoryService;

@RestController
@RequiredArgsConstructor
public class ParentCategoryController {

    private final ParentCategoryService parentCategoryService;

    //상위 게시판 생성 api
    @PostMapping("/blog/board/parent")
    public Response<ParentCategory> saveParentCategory(@RequestBody ParentCategorySaveRequestDto parentCategorySaveRequestDto) {
        return parentCategoryService.save(parentCategorySaveRequestDto);
    }
}
