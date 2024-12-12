package xeat.blogservice.notice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xeat.blogservice.article.entity.Article;
import xeat.blogservice.article.repository.ArticleRepository;
import xeat.blogservice.blog.entity.Blog;
import xeat.blogservice.blog.repository.BlogRepository;
import xeat.blogservice.codearticle.repository.CodeArticleRepository;
import xeat.blogservice.global.response.PageResponseDto;
import xeat.blogservice.global.response.Response;
import xeat.blogservice.global.response.ResponseDto;
import xeat.blogservice.global.feignclient.UserFeignClient;
import xeat.blogservice.global.feignclient.UserInfoResponseDto;
import xeat.blogservice.notice.dto.*;

import xeat.blogservice.notice.entity.Notice;
import xeat.blogservice.notice.entity.NoticeCategory;
import xeat.blogservice.notice.repository.NoticeRepository;
import xeat.blogservice.reply.entity.Reply;
import xeat.blogservice.reply.repository.ReplyRepository;
import xeat.blogservice.report.entity.ReportCategory;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NoticeService {

    private final BlogRepository blogRepository;
    private final NoticeRepository noticeRepository;
    private final UserFeignClient userFeignClient;

    @Transactional
    public Response<NoticeListPageResponseDto> getNoticeList(int page, int size, String userId) {
        Long blogId = blogRepository.findByUserId(userId).get().getId();
        Page<Notice> noticePageList = noticeRepository.findNoticeList(PageRequest.of(page, size), blogId);
        PageResponseDto pageInfo = PageResponseDto.noticeDto(noticePageList);
        List<ResponseDto> noticeList = new ArrayList<>();

        for (Notice notice : noticePageList) {
            String nickName = "관리자";
            if (notice.getSentUser() != null) {
                nickName = userFeignClient.getUserInfo(notice.getSentUser().getUserId()).getNickName();
            }

            if (notice.getNoticeCategory() == NoticeCategory.PARENT_REPLY ||
                    notice.getNoticeCategory() == NoticeCategory.CHILD_REPLY ||
                    notice.getNoticeCategory() == NoticeCategory.MENTIONED_USER) {
                noticeList.add(GetReplyArticleListResponseDto.toDto(notice, nickName));
            }
            else {
                noticeList.add(GetNoticeListResponseDto.toDto(notice, nickName));
            }
        }
        return Response.success(NoticeListPageResponseDto.toDto(pageInfo, noticeList));
    }

    @Transactional
    public Response<NoticeCheckResponseDto> checkNotice(String userId) {
        Blog blog = blogRepository.findByUserId(userId).get();
        blog.updateNoticeCheckTrue();
        blogRepository.save(blog);
        return Response.success(NoticeCheckResponseDto.toDto(blog));
    }

    @Transactional
    public void saveReplyNotice(Blog blog, Reply reply) {

        NoticeCategory noticeCategory = NoticeCategory.PARENT_REPLY;
        if (reply.getParentReplyId() != null) {
            noticeCategory = NoticeCategory.CHILD_REPLY;
        }
        // 알림 테이블에 댓글 작성 추가
        Notice notice = Notice.builder()
                .blog(blog)
                .reply(reply)
                .sentUser(reply.getUser())
                .article(reply.getArticle())
                .noticeCategory(noticeCategory)
                .content(reply.getContent())
                .build();

        noticeRepository.save(notice);
    }

    @Transactional
    public void saveMentionedUserNotice(Blog blog, Reply reply) {

        Notice notice = Notice.builder()
                .blog(blog)
                .reply(reply)
                .sentUser(reply.getUser())
                .article(reply.getArticle())
                .noticeCategory(NoticeCategory.MENTIONED_USER)
                .content(reply.getContent())
                .build();

        noticeRepository.save(notice);
    }

    @Transactional
    public Response<Notice> saveCodeNotice(CodeNoticeSaveRequestDto codeNoticeSaveRequestDto) {
        Blog blog = blogRepository.findByUserId(codeNoticeSaveRequestDto.getUserId()).get();
        blog.updateNoticeCheckFalse();
        blogRepository.save(blog);

        Notice notice = Notice.builder()
                .blog(blog)
                .noticeCategory(NoticeCategory.CODE)
                .content(codeNoticeSaveRequestDto.getTitle())
                .build();
        noticeRepository.save(notice);
        return Response.success(notice);
    }

    @Transactional
    public void saveArticleDeleteNotice(Article article, ArticleNoticeRequestDto articleNoticeRequestDto) {
        Notice notice = Notice.builder()
                .blog(blogRepository.findById(article.getBlog().getId()).get())
                .noticeCategory(NoticeCategory.ARTICLE_DELETE)
                .reasonCategory(articleNoticeRequestDto.getReasonCategory())
                .directCategory(articleNoticeRequestDto.getDirectCategory())
                .content(article.getTitle())
                .build();
        noticeRepository.save(notice);
    }

    @Transactional
    public void saveArticleBlindNotice(Article article, ArticleNoticeRequestDto articleNoticeRequestDto) {
        Notice notice = Notice.builder()
                .blog(blogRepository.findById(article.getBlog().getId()).get())
                .noticeCategory(NoticeCategory.BLIND)
                .reasonCategory(articleNoticeRequestDto.getReasonCategory())
                .directCategory(articleNoticeRequestDto.getDirectCategory())
                .content(article.getTitle())
                .build();
        noticeRepository.save(notice);
    }

    @Transactional
    public void saveArticleNonBlindNotice(Article article) {
        Notice notice = Notice.builder()
                .blog(blogRepository.findById(article.getBlog().getId()).get())
                .noticeCategory(NoticeCategory.NON_BLIND)
                .content(article.getTitle())
                .build();
        noticeRepository.save(notice);
    }

    @Transactional
    public void saveReplyDeleteNotice(Reply reply, ReplyNoticeDeleteRequestDto replyNoticeDeleteRequestDto) {
        Notice notice = Notice.builder()
                .blog(reply.getUser())
                .noticeCategory(NoticeCategory.REPLY_DELETE)
                .reasonCategory(replyNoticeDeleteRequestDto.getReasonCategory())
                .directCategory(replyNoticeDeleteRequestDto.getDirectCategory())
                .content(reply.getContent())
                .build();
        noticeRepository.save(notice);
    }


}