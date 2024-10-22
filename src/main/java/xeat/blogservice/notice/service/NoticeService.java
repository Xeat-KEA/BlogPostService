package xeat.blogservice.notice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xeat.blogservice.blog.entity.Blog;
import xeat.blogservice.blog.repository.BlogRepository;
import xeat.blogservice.global.Response;
import xeat.blogservice.notice.dto.GetNoticeListResponseDto;
import xeat.blogservice.notice.entity.Notice;
import xeat.blogservice.notice.repository.NoticeRepository;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final BlogRepository blogRepository;
    private final NoticeRepository noticeRepository;

    @Transactional
    public Response<?> getNoticeList(Long blogId) {
        List<Notice> noticeList = noticeRepository.findNoticeList(blogId);
        List<GetNoticeListResponseDto> noticeListDto = new ArrayList<>();

        noticeList.forEach(n -> noticeListDto.add(GetNoticeListResponseDto.toDto(n)));
        return Response.success(noticeListDto);
    }

    @Transactional
    public Response<Blog> checkNotice(Long blogId) {
        Blog blog = blogRepository.findById(blogId).get();
        blog.updateNoticeCheckTrue();
        blogRepository.save(blog);
        return Response.success(blog);
    }
}
