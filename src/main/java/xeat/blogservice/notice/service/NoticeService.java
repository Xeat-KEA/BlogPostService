package xeat.blogservice.notice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xeat.blogservice.blog.entity.Blog;
import xeat.blogservice.blog.repository.BlogRepository;
import xeat.blogservice.global.Response;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final BlogRepository blogRepository;

    public Response<Blog> checkNotice(Long blogId) {
        Blog blog = blogRepository.findById(blogId).get();
        blog.updateNoticeCheckTrue();
        blogRepository.save(blog);
        return Response.success(blog);
    }
}
