package xeat.blogservice.childcategory.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xeat.blogservice.blog.repository.BlogRepository;
import xeat.blogservice.childcategory.dto.ChildCategoryCreateRequestDto;
import xeat.blogservice.childcategory.dto.ChildCategoryCreateResponseDto;
import xeat.blogservice.childcategory.dto.ChildCategoryEditRequestDto;
import xeat.blogservice.childcategory.entity.ChildCategory;
import xeat.blogservice.childcategory.repository.ChildCategoryRepository;
import xeat.blogservice.global.Response;
import xeat.blogservice.parentcategory.repository.ParentCategoryRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChildCategoryService {

    private final BlogRepository blogRepository;
    private final ParentCategoryRepository parentCategoryRepository;
    private final ChildCategoryRepository childCategoryRepository;

    public Response<ChildCategoryCreateResponseDto> create(ChildCategoryCreateRequestDto childCategoryCreateRequestDto) {

        ChildCategory childCategory = ChildCategory.builder()
                .blog(blogRepository.findById(childCategoryCreateRequestDto.getBlogId()).get())
                .parentCategory(parentCategoryRepository.findById(childCategoryCreateRequestDto.getParentCategoryId()).get())
                .childName(childCategoryCreateRequestDto.getChildName())
                .build();

        childCategoryRepository.save(childCategory);
        return Response.success(ChildCategoryCreateResponseDto.toDto(childCategory));
    }

    @Transactional
    public Response<ChildCategory> edit(Long childCategoryId, ChildCategoryEditRequestDto childCategoryEditRequestDto) {
        ChildCategory childCategory = childCategoryRepository.findById(childCategoryId).get();
        childCategory.updateChildName(childCategoryEditRequestDto.getChildName());
        childCategoryRepository.save(childCategory);
        return Response.success(childCategory);
    }
}
