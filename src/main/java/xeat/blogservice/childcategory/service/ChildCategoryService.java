package xeat.blogservice.childcategory.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xeat.blogservice.blog.repository.BlogRepository;
import xeat.blogservice.childcategory.dto.ChildCategoryCreateRequestDto;
import xeat.blogservice.childcategory.entity.ChildCategory;
import xeat.blogservice.global.Response;
import xeat.blogservice.parentcategory.repository.ParentCategoryRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChildCategoryService {

    private final BlogRepository blogRepository;
    private final ParentCategoryRepository parentCategoryRepository;

    public Response<ChildCategory> create(ChildCategoryCreateRequestDto childCategoryCreateRequestDto) {

        ChildCategory childCategory = ChildCategory.builder()
                .blog(blogRepository.findById(childCategoryCreateRequestDto.getBlogId()).get())
                .parentCategory(parentCategoryRepository.findById(childCategoryCreateRequestDto.getParentCategoryId()).get())
                .childName(childCategoryCreateRequestDto.getChildName())
                .build();

        return Response.success(childCategory);
    }
}
