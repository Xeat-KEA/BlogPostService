package xeat.blogservice.parentcategory.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import xeat.blogservice.global.Response;
import xeat.blogservice.parentcategory.dto.CategoryListResponseDto;
import xeat.blogservice.parentcategory.dto.ParentCategoryCreateResponseDto;
import xeat.blogservice.parentcategory.dto.ParentCategoryEditRequestDto;
import xeat.blogservice.parentcategory.dto.ParentCategorySaveRequestDto;
import xeat.blogservice.parentcategory.entity.ParentCategory;
import xeat.blogservice.parentcategory.service.ParentCategoryService;

import java.util.List;

@Tag(name = "상위 게시판", description = "상위 게시판 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/blog/board")
public class ParentCategoryController {

    private final ParentCategoryService parentCategoryService;

    // 블로그 게시판 목록 조회 api
    @Operation(summary = "게시판 목록 조회(상위 게시판, 하위 게시판 모두)", description = "블로그 내의 모든 게시판 목록 조회 시 필요한 API")
    @GetMapping("/list")
    public Response<List<CategoryListResponseDto>> getCategoryList(@RequestHeader("UserId") String userId) {
        return parentCategoryService.getCategoryList(userId);
    }


    //상위 게시판 생성 api
    @Operation(summary = "상위 게시판 생성", description = "상위 게시판 생성 시 필요한 API")
    @PostMapping("/parent")
    public Response<ParentCategoryCreateResponseDto> saveParentCategory(@RequestHeader("UserId") String userId, @RequestBody ParentCategorySaveRequestDto parentCategorySaveRequestDto) {
        return parentCategoryService.save(userId, parentCategorySaveRequestDto);
    }

    //상위 게시판 이름 수정 api
    @Operation(summary = "상위 게시판 이름 수정", description = "상위 게시판 이름 수정 시 필요한 API")
    @PutMapping("/parent/{parentCategoryId}")
    public Response<ParentCategory> editParentCategoryName(@PathVariable Long parentCategoryId, @RequestBody ParentCategoryEditRequestDto parentCategoryEditRequestDto) {
        return parentCategoryService.edit(parentCategoryId, parentCategoryEditRequestDto);
    }

    //상위 게시판 삭제
    @Operation(summary = "상위 게시판 삭제", description = "상위 게시판 삭제 시 상위 게시판에 속한 모든 하위게시판도 자동 삭제")
    @DeleteMapping("/parent/{parentCategoryId}")
    public Response<?> deleteParentCategory(@PathVariable Long parentCategoryId) {
        return parentCategoryService.delete(parentCategoryId);
    }
}
