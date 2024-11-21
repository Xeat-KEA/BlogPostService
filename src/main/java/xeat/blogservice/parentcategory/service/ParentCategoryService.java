package xeat.blogservice.parentcategory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xeat.blogservice.blog.repository.BlogRepository;
import xeat.blogservice.childcategory.dto.ChildCategoryResponseDto;
import xeat.blogservice.childcategory.entity.ChildCategory;
import xeat.blogservice.childcategory.repository.ChildCategoryRepository;
import xeat.blogservice.global.Response;
import xeat.blogservice.parentcategory.dto.*;
import xeat.blogservice.parentcategory.entity.ParentCategory;
import xeat.blogservice.parentcategory.repository.ParentCategoryRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ParentCategoryService {

    private final BlogRepository blogRepository;
    private final ParentCategoryRepository parentCategoryRepository;
    private final ChildCategoryRepository childCategoryRepository;


    // 게시판 목록 조회
    @Transactional
    public Response<List<CategoryListResponseDto>> getCategoryList(String userId) {
        Long blogId = blogRepository.findByUserId(userId).get().getId();

        List<CategoryListResponseDto> categoryListResponseDtoList = new ArrayList<>();

        // 모든 사용자에게 코딩테스트 풀이 게시판 및 그에 해당하는 하위게시판 포함
        ParentCategory codingTestParentCategory = parentCategoryRepository.findById(1L).get();
        categoryListResponseDtoList.add(CategoryListResponseDto.toDto(codingTestParentCategory, getChildCategories(1L)));

        List<ParentCategory> parentCategories = parentCategoryRepository.findAllByBlogId(blogId);
        parentCategories.forEach(s -> categoryListResponseDtoList.add(CategoryListResponseDto.toDto(s, getChildCategories(s.getId()))));
        return Response.success(categoryListResponseDtoList);
    }

    // 상위 게시판 저장
    @Transactional
    public Response<ParentCategoryCreateResponseDto> save(String userId, ParentCategorySaveRequestDto parentCategorySaveRequestDto) {
        ParentCategory parentCategory = ParentCategory.builder()
                .blog(blogRepository.findByUserId(userId).get())
                .parentName(parentCategorySaveRequestDto.getParentName())
                .build();

        parentCategoryRepository.save(parentCategory);
        ChildCategory childCategory = ChildCategory.builder()
                .parentCategory(parentCategory)
                .childName("하위 게시판")
                .build();
        childCategoryRepository.save(childCategory);
        return Response.success(ParentCategoryCreateResponseDto.toDto(parentCategory));
    }

    // 상위 게시판 이름 수정
    @Transactional
    public Response<ParentCategory> edit(Long parentCategoryId, ParentCategoryEditRequestDto parentCategoryEditRequestDto) {

        ParentCategory parentCategory = parentCategoryRepository.findById(parentCategoryId).get();
        parentCategory.updateParentName(parentCategoryEditRequestDto.getParentName());
        return Response.success(parentCategoryRepository.save(parentCategory));
    }

    @Transactional
    public Response<?> delete(Long parentCategoryId) {
        parentCategoryRepository.deleteById(parentCategoryId);
        return new Response<>(200, "상위게시판 삭제 성공", null);
    }

    public List<ChildCategoryResponseDto> getChildCategories(Long parentCategoryId) {
        List<ChildCategory> childCategories = childCategoryRepository.findAllByParentCategoryId(parentCategoryId);
        List<ChildCategoryResponseDto> childCategoryResponseDtoList = new ArrayList<>();

        childCategories.forEach(s -> childCategoryResponseDtoList.add(ChildCategoryResponseDto.toDto(s)));
        return childCategoryResponseDtoList;
    }
}
