package xeat.blogservice.childcategory.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import xeat.blogservice.childcategory.dto.ChildCategoryCreateRequestDto;
import xeat.blogservice.childcategory.dto.ChildCategoryResponseDto;
import xeat.blogservice.childcategory.dto.ChildCategoryEditRequestDto;
import xeat.blogservice.childcategory.entity.ChildCategory;
import xeat.blogservice.childcategory.service.ChildCategoryService;
import xeat.blogservice.global.Response;

@RestController
@RequiredArgsConstructor
public class ChildCategoryController {

    private final ChildCategoryService childCategoryService;

    @PostMapping("/blog/board/child")
    public Response<ChildCategoryResponseDto> createChildCategory(@RequestBody ChildCategoryCreateRequestDto childCategoryCreateRequestDto) {
        return childCategoryService.create(childCategoryCreateRequestDto);
    }

    @PutMapping("/blog/board/child/edit/{childCategoryId}")
    public Response<ChildCategory> editChildCategoryName(@PathVariable Long childCategoryId, @RequestBody ChildCategoryEditRequestDto childCategoryEditRequestDto) {
        return childCategoryService.edit(childCategoryId, childCategoryEditRequestDto);
    }

    @DeleteMapping("/blog/board/child/delete/{childCategoryId}")
    public Response<?> deleteChildCategory(@PathVariable Long childCategoryId) {
        return childCategoryService.delete(childCategoryId);
    }
}
