package xeat.blogservice.parentcategory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xeat.blogservice.blog.repository.BlogRepository;
import xeat.blogservice.childcategory.dto.ChildCategoryResponseDto;
import xeat.blogservice.childcategory.entity.ChildCategory;
import xeat.blogservice.childcategory.repository.ChildCategoryRepository;
import xeat.blogservice.global.response.Response;
import xeat.blogservice.global.feignclient.UserFeignClient;
import xeat.blogservice.global.feignclient.UserInfoResponseDto;
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
    private final UserFeignClient userFeignClient;


    // 게시판 목록 조회
    @Transactional
    public Response<CategoryTotalResponseDto> getCategoryList(Long blogId) {
        UserInfoResponseDto userInfo = userFeignClient.getUserInfo(blogRepository.findById(blogId).get().getUserId());

        List<CategoryListResponseDto> categoryListResponseDtoList = new ArrayList<>();

        // 모든 사용자에게 코딩테스트 풀이 게시판 및 그에 해당하는 하위게시판 포함
        ParentCategory codingTestParentCategory = parentCategoryRepository.findById(1L).get();
        categoryListResponseDtoList.add(CategoryListResponseDto.toDto(codingTestParentCategory, getChildCategories(1L)));

        List<ParentCategory> parentCategories = parentCategoryRepository.findAllByBlogId(blogId);
        parentCategories.forEach(s -> categoryListResponseDtoList.add(CategoryListResponseDto.toDto(s, getChildCategories(s.getId()))));
        return Response.success(CategoryTotalResponseDto.toDto(userInfo.getNickName(), categoryListResponseDtoList));
    }

    @Transactional
    public Response<CategoryListResponseDto> getChildCategoryList(Long parentCategoryId) {
        ParentCategory parentCategory = parentCategoryRepository.findById(parentCategoryId).get();
        List<ChildCategoryResponseDto> childCategories = getChildCategories(parentCategoryId);
        return Response.success(CategoryListResponseDto.toDto(parentCategory, childCategories));
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
