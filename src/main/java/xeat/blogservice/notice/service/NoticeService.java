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
import xeat.blogservice.global.PageResponseDto;
import xeat.blogservice.global.Response;
import xeat.blogservice.global.ResponseDto;
import xeat.blogservice.global.feignclient.UserFeignClient;
import xeat.blogservice.global.feignclient.UserInfoResponseDto;
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
@Slf4j
public class NoticeService {

    private final BlogRepository blogRepository;
    private final NoticeRepository noticeRepository;
    private final ArticleRepository articleRepository;
    private final ReplyRepository replyRepository;
    private final CodeArticleRepository codeArticleRepository;
    private final UserFeignClient userFeignClient;

    @Transactional
    public Response<NoticeListPageResponseDto> getNoticeList(int page, int size, String userId) {
        Long blogId = blogRepository.findByUserId(userId).get().getId();
        Page<Notice> noticePageList = noticeRepository.findNoticeList(PageRequest.of(page, size), blogId);
        PageResponseDto pageInfo = PageResponseDto.noticeDto(noticePageList);
        List<ResponseDto> noticeList = new ArrayList<>();

        for (Notice notice : noticePageList) {
            UserInfoResponseDto userInfo = userFeignClient.getUserInfo(notice.getSentUser().getUserId());
            if (notice.getNoticeCategory() == NoticeCategory.REPLY) {
                noticeList.add(GetReplyArticleListResponseDto.toDto(notice, userInfo.getNickName()));
                log.info("NoticeCategory={}", notice.getNoticeCategory());
            }
            else {
                noticeList.add(GetNoticeListResponseDto.toDto(notice, userInfo.getNickName()));
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

    // userId로 해당 사용자의 닉네임 가져오는 method
    public String getNickNameByUserId(String userId) {
        UserInfoResponseDto userInfo = userFeignClient.getUserInfo(userId);
        return userInfo.getNickName();
    }
}
