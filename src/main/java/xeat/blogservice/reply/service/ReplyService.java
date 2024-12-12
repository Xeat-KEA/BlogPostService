package xeat.blogservice.reply.service;

import co.elastic.clients.elasticsearch.nodes.Ingest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xeat.blogservice.article.entity.Article;
import xeat.blogservice.article.repository.ArticleRepository;
import xeat.blogservice.blog.entity.Blog;
import xeat.blogservice.blog.repository.BlogRepository;
import xeat.blogservice.global.response.Response;
import xeat.blogservice.global.feignclient.UserFeignClient;
import xeat.blogservice.global.feignclient.UserInfoResponseDto;
import xeat.blogservice.notice.dto.ReplyNoticeDeleteRequestDto;
import xeat.blogservice.notice.service.NoticeService;
import xeat.blogservice.reply.dto.ReplyEditRequestDto;
import xeat.blogservice.reply.dto.ReplyPostRequestDto;
import xeat.blogservice.reply.dto.ReplyResponseDto;
import xeat.blogservice.reply.entity.Reply;
import xeat.blogservice.reply.repository.ReplyRepository;

@Service
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyRepository replyRepository;

    private final ArticleRepository articleRepository;

    private final BlogRepository blogRepository;

    private final NoticeService noticeService;

    private final UserFeignClient userFeignClient;

    @Transactional
    public Response<ReplyResponseDto> replyPost(String userId, ReplyPostRequestDto replyPostRequestDto) {

        Blog mentionedUser = null;
        if (replyPostRequestDto.getMentionedUserBlogId() != null) {
            mentionedUser = blogRepository.findById(replyPostRequestDto.getMentionedUserBlogId()).get();
        }

        Reply reply = Reply.builder()
                .article(articleRepository.findById(replyPostRequestDto.getArticleId()).get())
                .user(blogRepository.findByUserId(userId).get())
                .mentionedUser(mentionedUser)
                .parentReplyId(replyPostRequestDto.getParentReplyId())
                .content(replyPostRequestDto.getContent())
                .build();
        replyRepository.save(reply);

        Article article = articleRepository.findById(reply.getArticle().getId()).get();
        article.plusReplyCount();
        articleRepository.save(article);

        Blog blog = blogRepository.findById(reply.getArticle().getBlog().getId()).get();
        if (reply.getParentReplyId() != null) { // 하위 댓글 일 시

            // 상위 댓글 사용자 블로그
            Reply parentReply = replyRepository.findById(reply.getParentReplyId()).get();
            Blog parentReplyBlog = blogRepository.findById(parentReply.getUser().getId()).get();

            //언급된 사용자 블로그
            Blog mentionedUserBlog = reply.getMentionedUser();

            //언급된 사용자 알림
            if (reply.getUser() != mentionedUserBlog) {
                noticeService.saveMentionedUserNotice(mentionedUserBlog, reply);
                mentionedUserBlog.updateNoticeCheckFalse();
                blogRepository.save(mentionedUserBlog);
            }

            if (parentReplyBlog != mentionedUserBlog && reply.getUser() != parentReplyBlog) { // 상위 댓글 사용자와 언급된 사용자가 다를 시
                noticeService.saveReplyNotice(parentReplyBlog, reply);
                parentReplyBlog.updateNoticeCheckFalse();
                blogRepository.save(parentReplyBlog);
            }
        }

        else {
            if (reply.getUser() != blog) { // 댓글 작성자와 게시글 작성자가 다를 시
                noticeService.saveReplyNotice(blog, reply);
                blog.updateNoticeCheckFalse();
                blogRepository.save(blog);
            }
        }



        if (reply.getMentionedUser() == null) {
            return Response.success(ReplyResponseDto.parentReplyDto(reply, getNickNameByUserId(userId)));
        }
        else {
            return Response.success(ReplyResponseDto.childReplyDto(reply, getNickNameByUserId(userId),
                    getNickNameByUserId(reply.getMentionedUser().getUserId())));
        }
    }

    @Transactional
    public Response<ReplyResponseDto> replyEdit(Long replyId, ReplyEditRequestDto replyEditRequestDto) {
        Reply reply = replyRepository.findById(replyId).get();
        reply.editContent(replyEditRequestDto.getContent());
        replyRepository.save(reply);
        if (reply.getMentionedUser() == null) {
            return Response.success(ReplyResponseDto.parentReplyDto(reply, getNickNameByUserId(reply.getUser().getUserId())));
        }
        else {
            return Response.success(ReplyResponseDto.childReplyDto(reply, getNickNameByUserId(reply.getUser().getUserId()), getNickNameByUserId(reply.getMentionedUser().getUserId())));
        }
    }

    @Transactional
    public Response<?> delete(Long replyId) {

        editBlogCount(replyId);

        replyRepository.deleteById(replyId);

        return new Response<>(200, "댓글 삭제 완료", null);
    }

    @Transactional
    public Response<?> deleteReplyByAdmin(ReplyNoticeDeleteRequestDto replyNoticeDeleteRequestDto) {

        Reply reply = replyRepository.findById(replyNoticeDeleteRequestDto.getReplyId()).get();

        Blog blog = blogRepository.findByUserId(reply.getUser().getUserId()).get();
        blog.updateNoticeCheckFalse();
        blogRepository.save(blog);

        editBlogCount(reply.getId());

        noticeService.saveReplyDeleteNotice(reply, replyNoticeDeleteRequestDto);

        replyRepository.deleteById(reply.getId());

        return new Response<>(200, "댓글 삭제 및 알림 등록 성공", null);
    }

    public String getNickNameByUserId(String userId) {
        UserInfoResponseDto userInfo = userFeignClient.getUserInfo(userId);
        return userInfo.getNickName();
    }

    public void editBlogCount(Long replyId) {
        Reply reply = replyRepository.findById(replyId).get();
        Article article = articleRepository.findById(reply.getArticle().getId()).get();

        if (reply.getParentReplyId() == null) {
            Integer replyCount = replyRepository.countByParentReplyId(reply.getId());
            article.minusReplyCount(replyCount + 1);
        }
        else {
            article.minusReplyCount(1);
        }
        articleRepository.save(article);
    }
}
