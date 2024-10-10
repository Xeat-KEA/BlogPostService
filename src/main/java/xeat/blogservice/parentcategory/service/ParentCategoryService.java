package xeat.blogservice.parentcategory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xeat.blogservice.blog.repository.BlogRepository;
import xeat.blogservice.global.Response;
import xeat.blogservice.parentcategory.dto.ParentCategorySaveRequestDto;
import xeat.blogservice.parentcategory.entity.ParentCategory;
import xeat.blogservice.parentcategory.repository.ParentCategoryRepository;

@Service
@RequiredArgsConstructor
public class ParentCategoryService {

    private final BlogRepository blogRepository;
    private final ParentCategoryRepository parentCategoryRepository;

    // 상위 게시판 저장
    @Transactional
    public Response<ParentCategory> save(ParentCategorySaveRequestDto parentCategorySaveRequestDto) {

        ParentCategory parentCategory = ParentCategory.builder()
                .blog(blogRepository.findById(parentCategorySaveRequestDto.getBlogId()).get())
                .parentName(parentCategorySaveRequestDto.getParentName())
                .build();

        return Response.success(parentCategoryRepository.save(parentCategory));
    }
}
