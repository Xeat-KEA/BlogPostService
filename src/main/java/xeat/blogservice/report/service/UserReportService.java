package xeat.blogservice.report.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xeat.blogservice.article.entity.Article;
import xeat.blogservice.article.repository.ArticleRepository;
import xeat.blogservice.blog.repository.BlogRepository;
import xeat.blogservice.codearticle.dto.CodeArticleListResponseDto;
import xeat.blogservice.codearticle.entity.CodeArticle;
import xeat.blogservice.global.PageResponseDto;
import xeat.blogservice.global.Response;
import xeat.blogservice.global.ResponseDto;
import xeat.blogservice.global.feignclient.UserFeignClient;
import xeat.blogservice.global.feignclient.UserInfoResponseDto;
import xeat.blogservice.notice.entity.Notice;
import xeat.blogservice.notice.entity.NoticeCategory;
import xeat.blogservice.notice.repository.NoticeRepository;
import xeat.blogservice.reply.repository.ReplyRepository;
import xeat.blogservice.report.dto.*;
import xeat.blogservice.report.entity.ReportCategory;
import xeat.blogservice.report.entity.UserReport;
import xeat.blogservice.report.repository.UserReportRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserReportService {


    private final UserReportRepository userReportRepository;
    private final BlogRepository blogRepository;
    private final ArticleRepository articleRepository;
    private final ReplyRepository replyRepository;
    private final NoticeRepository noticeRepository;
    private final UserFeignClient userFeignClient;

    @Transactional
    public Response<ReportListPageResponseDto> getArticleReportList(int page, int size) {

        Page<UserReport> articleReportPage = userReportRepository.findByArticleIsNotNull(PageRequest.of(page, size));

        PageResponseDto pageInfo = PageResponseDto.userReportDto(articleReportPage);

        List<ResponseDto> articleReportListDto = new ArrayList<>();

        articleReportPage.getContent().forEach(s-> articleReportListDto.add(ArticleReportListResponseDto.toDto(s, getNickNameByUserId(s.getReportUser().getUserId()))));

        return Response.success(ReportListPageResponseDto.toDto(pageInfo, articleReportListDto));
    }

    @Transactional
    public Response<ReportListPageResponseDto> getBlogReportList(int page, int size) {

        Page<UserReport> blogReportPage = userReportRepository.findByBlogIsNotNull(PageRequest.of(page, size));

        PageResponseDto pageInfo = PageResponseDto.userReportDto(blogReportPage);

        List<ResponseDto> blogReportListDto = new ArrayList<>();

        blogReportPage.getContent().forEach(s->
                blogReportListDto.add(BlogReportListResponseDto.toDto(s,
                        getNickNameByUserId(s.getReportUser().getUserId()),
                        getNickNameByUserId(s.getBlog().getUserId()))));

        return Response.success(ReportListPageResponseDto.toDto(pageInfo, blogReportListDto));
    }

    @Transactional
    public Response<ReportListPageResponseDto> getReplyReportList(int page, int size) {

        Page<UserReport> replyReportPage = userReportRepository.findByReplyIsNotNull(PageRequest.of(page, size));

        PageResponseDto pageInfo = PageResponseDto.userReportDto(replyReportPage);

        List<ResponseDto> replyReportListDto = new ArrayList<>();

        replyReportPage.getContent().forEach(s->
                replyReportListDto.add(ReplyReportListResponseDto.toDto(s,
                        getNickNameByUserId(s.getReportUser().getUserId()))));

        return Response.success(ReportListPageResponseDto.toDto(pageInfo, replyReportListDto));
    }


    @Transactional
    public Response<BlogReportResponseDto> reportBlog(Long blogId, String userId, ReportRequestDto reportRequestDto) {

        UserReport userReport = UserReport.builder()
                .blog(blogRepository.findById(blogId).get())
                .reportUser(blogRepository.findByUserId(userId).get())
                .reportCategory(reportRequestDto.getReportCategory())
                .directCategory(reportRequestDto.getDirectCategory())
                .build();

        userReportRepository.save(userReport);
        return Response.success(BlogReportResponseDto.toDto(userReport, getNickNameByUserId(userId)));
    }

    @Transactional
    public Response<ArticleReportResponseDto> reportArticle(Long articleId, String userId, ReportRequestDto reportRequestDto) {

        Article article = articleRepository.findById(articleId).get();

        UserReport userReport = UserReport.builder()
                .article(article)
                .reportUser(blogRepository.findByUserId(userId).get())
                .reportCategory(reportRequestDto.getReportCategory())
                .directCategory(reportRequestDto.getDirectCategory())
                .build();

        // 게시글 신고 수 +1 and 10개가 될 경우 isBlind = True 처리
        article.plusReportCount();

        if (article.getReportCount() == 10) {
            // 게시글 블러처리
            article.updateIsBlindTrue(true);

            // 블러처리 알림 등록
            Notice notice = Notice.builder()
                    .blog(article.getBlog())
                    .noticeCategory(NoticeCategory.Blur)
                    .reasonCategory(reportRequestDto.getReportCategory())
                    .content(article.getTitle())
                    .build();
            noticeRepository.save(notice);
        }
        return Response.success(ArticleReportResponseDto.toDto(userReportRepository.save(userReport),
                                                                articleRepository.save(article), userId));
    }

    @Transactional
    public Response<ReplyReportResponseDto> reportReply(Long replyId, String userId, ReportRequestDto reportRequestDto) {

        UserReport userReport = UserReport.builder()
                .reply(replyRepository.findById(replyId).get())
                .reportUser(blogRepository.findByUserId(userId).get())
                .reportCategory(reportRequestDto.getReportCategory())
                .directCategory(reportRequestDto.getDirectCategory())
                .build();

        userReportRepository.save(userReport);
        return Response.success(ReplyReportResponseDto.toDto(userReport, userId));
    }

    public String getNickNameByUserId(String userId) {
        UserInfoResponseDto userInfo = userFeignClient.getUserInfo(userId);
        return userInfo.getNickName();
    }


}
