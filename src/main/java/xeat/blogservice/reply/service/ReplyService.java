package xeat.blogservice.reply.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xeat.blogservice.article.repository.ArticleRepository;
import xeat.blogservice.blog.entity.Blog;
import xeat.blogservice.blog.repository.BlogRepository;
import xeat.blogservice.global.Response;
import xeat.blogservice.notice.entity.Notice;
import xeat.blogservice.notice.entity.NoticeCategory;
import xeat.blogservice.notice.repository.NoticeRepository;
import xeat.blogservice.reply.dto.ReplyPostRequestDto;
import xeat.blogservice.reply.dto.ReplyPostResponseDto;
import xeat.blogservice.reply.entity.Reply;
import xeat.blogservice.reply.repository.ReplyRepository;

@Service
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyRepository replyRepository;

    private final ArticleRepository articleRepository;

    private final BlogRepository blogRepository;

    private final NoticeRepository noticeRepository;

    @Transactional
    public Response<ReplyPostResponseDto> replyPost(ReplyPostRequestDto replyPostRequestDto) {

        Reply reply = Reply.builder()
                .article(articleRepository.findById(replyPostRequestDto.getArticleId()).get())
                .user(blogRepository.findByUserId(replyPostRequestDto.getUserId()).get())
                .parentReplyId(replyPostRequestDto.getParentReplyId())
                .content(replyPostRequestDto.getContent())
                .build();
        replyRepository.save(reply);

        // 블로그 알림 상태 확인 false로 업데이트
        Blog blog = blogRepository.findById(reply.getArticle().getBlog().getId()).get();
        blog.updateNoticeCheckFalse();
        blogRepository.save(blog);


        // 알림 table에 추가
        Notice notice = Notice.builder()
                .blog(blog)
                .sentUser(reply.getUser())
                .noticeCategory(NoticeCategory.REPLY)
                .content(reply.getContent())
                .build();

        noticeRepository.save(notice);

        return Response.success(ReplyPostResponseDto.toDto(reply));
    }
}
