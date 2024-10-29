package xeat.blogservice.childcategory.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import xeat.blogservice.childcategory.dto.ChildCategoryCreateRequestDto;
import xeat.blogservice.childcategory.dto.ChildCategoryResponseDto;
import xeat.blogservice.childcategory.dto.ChildCategoryEditRequestDto;
import xeat.blogservice.childcategory.entity.ChildCategory;
import xeat.blogservice.childcategory.service.ChildCategoryService;
import xeat.blogservice.global.Response;

@RestController
@RequestMapping("/blog/board/child")
@RequiredArgsConstructor
@Tag(name = "하위 게시판", description = "하위 게시판 관련 API")
public class ChildCategoryController {

    private final ChildCategoryService childCategoryService;

    @Operation(summary = "하위 게시판 생성", description = "하위 게시판 생성 시 필요한 API")
    @PostMapping
    public Response<ChildCategoryResponseDto> createChildCategory(@RequestBody ChildCategoryCreateRequestDto childCategoryCreateRequestDto) {
        return childCategoryService.create(childCategoryCreateRequestDto);
    }

    @Operation(summary = "하위 게시판 수정", description = "하위 게시판 이름 수정 시 필요한 API")
    @PutMapping("/{childCategoryId}")
    public Response<ChildCategoryResponseDto> editChildCategoryName(@PathVariable Long childCategoryId, @RequestBody ChildCategoryEditRequestDto childCategoryEditRequestDto) {
        return childCategoryService.edit(childCategoryId, childCategoryEditRequestDto);
    }

    @Operation(summary = "하위 게시판 삭제", description = "하위 게시판 삭제 시 필요한 API")
    @DeleteMapping("/{childCategoryId}")
    public Response<?> deleteChildCategory(@PathVariable Long childCategoryId) {
        return childCategoryService.delete(childCategoryId);
    }
}
