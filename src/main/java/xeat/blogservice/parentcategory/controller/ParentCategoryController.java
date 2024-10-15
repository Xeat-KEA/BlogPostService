package xeat.blogservice.parentcategory.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import xeat.blogservice.global.Response;
import xeat.blogservice.parentcategory.dto.ParentCategoryEditRequestDto;
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

    //상위 게시판 이름 수정 api
    @PutMapping("/blog/board/parent/edit/{parentCategoryId}")
    public Response<ParentCategory> editParentCategoryName(@PathVariable Long parentCategoryId, @RequestBody ParentCategoryEditRequestDto parentCategoryEditRequestDto) {
        return parentCategoryService.edit(parentCategoryId, parentCategoryEditRequestDto);
    }
}
