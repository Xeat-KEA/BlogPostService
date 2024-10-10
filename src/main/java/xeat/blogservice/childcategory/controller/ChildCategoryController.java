package xeat.blogservice.childcategory.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import xeat.blogservice.childcategory.dto.ChildCategoryCreateRequestDto;
import xeat.blogservice.childcategory.entity.ChildCategory;
import xeat.blogservice.childcategory.service.ChildCategoryService;
import xeat.blogservice.global.Response;

@RestController
@RequiredArgsConstructor
public class ChildCategoryController {

    private final ChildCategoryService childCategoryService;

    @PostMapping("/blog/board/child")
    public Response<ChildCategory> createChildCategory(@RequestBody ChildCategoryCreateRequestDto childCategoryCreateRequestDto) {
        return childCategoryService.create(childCategoryCreateRequestDto);
    }
}
