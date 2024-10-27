package xeat.blogservice.blog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xeat.blogservice.blog.dto.BlogCreateRequestDto;
import xeat.blogservice.blog.dto.BlogEditRequestDto;
import xeat.blogservice.blog.dto.BlogMainContentResponseDto;
import xeat.blogservice.blog.entity.Blog;
import xeat.blogservice.blog.repository.BlogRepository;
import xeat.blogservice.global.Response;

@Service
@RequiredArgsConstructor
public class BlogService {

    private final BlogRepository blogRepository;

    @Transactional
    public Response<BlogMainContentResponseDto> getMainContent(Long blogId) {
        Blog blog = blogRepository.findById(blogId).get();
        return Response.success(BlogMainContentResponseDto.toDto(blog));
    }


    @Transactional
    // 블로그 게시판 생성
    public Response<Blog> create(BlogCreateRequestDto blogCreateRequestDto) {
        Blog blog = Blog.builder()
                .userId(blogCreateRequestDto.getUserId())
                .build();
        return Response.success(blogRepository.save(blog));
    }

    @Transactional
    // 블로그 소개글 수정
    public Response<Blog> editMainContent(Long blogId, BlogEditRequestDto blogEditRequestDto) {
        Blog blog = blogRepository.findById(blogId).get();
        blog.updateMainContent(blogEditRequestDto.getMainContent());
        return Response.success(blogRepository.save(blog));
    }
}
