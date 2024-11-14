package xeat.blogservice.notice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xeat.blogservice.article.entity.Article;
import xeat.blogservice.article.repository.ArticleRepository;
import xeat.blogservice.blog.entity.Blog;
import xeat.blogservice.blog.repository.BlogRepository;
import xeat.blogservice.codearticle.repository.CodeArticleRepository;
import xeat.blogservice.global.Response;
import xeat.blogservice.notice.dto.*;

import xeat.blogservice.notice.entity.Notice;
import xeat.blogservice.notice.entity.NoticeCategory;
import xeat.blogservice.notice.repository.NoticeRepository;
import xeat.blogservice.reply.entity.Reply;
import xeat.blogservice.reply.repository.ReplyRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final BlogRepository blogRepository;
    private final NoticeRepository noticeRepository;
    private final ArticleRepository articleRepository;
    private final ReplyRepository replyRepository;
    private final CodeArticleRepository codeArticleRepository;

    @Transactional
    public Response<List<GetNoticeListResponseDto>> getNoticeList(String userId) {
        Long blogId = blogRepository.findByUserId(userId).get().getId();
        List<Notice> noticeList = noticeRepository.findNoticeList(blogId);
        List<GetNoticeListResponseDto> noticeListDto = new ArrayList<>();

        noticeList.forEach(n -> noticeListDto.add(GetNoticeListResponseDto.toDto(n, n.getSentUser().getUserId())));
        return Response.success(noticeListDto);
    }

    @Transactional
    public Response<NoticeCheckResponseDto> checkNotice(String userId) {
        Blog blog = blogRepository.findByUserId(userId).get();
        blog.updateNoticeCheckTrue();
        blogRepository.save(blog);
        return Response.success(NoticeCheckResponseDto.toDto(blog));
    }

    @Transactional
    public Response<NoticeAdminSaveResponseDto> saveArticleDeleteNotice(ArticleNoticeDeleteRequestDto articleNoticeDeleteRequestDto) {
        Article article = articleRepository.findById(articleNoticeDeleteRequestDto.getArticleId()).get();
        Notice notice = Notice.builder()
                .blog(blogRepository.findById(article.getBlog().getId()).get())
                .noticeCategory(NoticeCategory.DELETE)
                .reasonCategory(articleNoticeDeleteRequestDto.getReasonCategory())
                .content(article.getTitle())
                .build();
        noticeRepository.save(notice);

        articleRepository.deleteById(article.getId());

        if (codeArticleRepository.existsByArticleId(article.getId())) {
            codeArticleRepository.delete(codeArticleRepository.findByArticleId(article.getId()).get());
        }

        return Response.success(NoticeAdminSaveResponseDto.toDto(notice));
    }

    @Transactional
    public Response<NoticeAdminSaveResponseDto> saveReplyDeleteNotice(ReplyDeleteNoticeRequestDto replyDeleteNoticeRequestDto) {

        Reply reply = replyRepository.findById(replyDeleteNoticeRequestDto.getReplyId()).get();
        Notice notice = Notice.builder()
                .blog(reply.getUser())
                .noticeCategory(NoticeCategory.DELETE)
                .reasonCategory(replyDeleteNoticeRequestDto.getReasonCategory())
                .content(reply.getContent())
                .build();

        noticeRepository.save(notice);

        replyRepository.delete(reply);
        return Response.success(NoticeAdminSaveResponseDto.toDto(notice));
    }
}
