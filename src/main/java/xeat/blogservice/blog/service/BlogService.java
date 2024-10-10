package xeat.blogservice.blog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xeat.blogservice.blog.dto.BlogCreateRequestDto;
import xeat.blogservice.blog.entity.Blog;
import xeat.blogservice.blog.repository.BlogRepository;
import xeat.blogservice.global.Response;

@Service
@RequiredArgsConstructor
public class BlogService {

    private final BlogRepository blogRepository;

    @Transactional
    // 블로그 게시판 생성
    public Response<Blog> create(BlogCreateRequestDto blogCreateRequestDto) {
        Blog blog = Blog.builder()
                .userId(blogCreateRequestDto.getUserId())
                .build();
        return Response.success(blogRepository.save(blog));
    }
}
