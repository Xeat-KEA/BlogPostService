package xeat.blogservice.reply.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
        Reply parentReply = null;


        if (replyPostRequestDto.getMentionedUserBlogId() != null) {
            parentReply = replyRepository.findById(replyPostRequestDto.getParentReplyId()).get();
            mentionedUser = blogRepository.findByUserId(parentReply.getUser().getUserId()).get();
        }

        Reply reply = Reply.builder()
                .article(articleRepository.findById(replyPostRequestDto.getArticleId()).get())
                .user(blogRepository.findByUserId(userId).get())
                .mentionedUser(mentionedUser)
                .parentReplyId(replyPostRequestDto.getParentReplyId())
                .content(replyPostRequestDto.getContent())
                .build();
        replyRepository.save(reply);

        // 블로그 알림 상태 확인 false로 업데이트
        Blog blog = blogRepository.findById(reply.getArticle().getBlog().getId()).get();

        // 만약 대댓글일 경우 상위 댓글 작성자의 블로그로 설정
        if (mentionedUser != null) {
            blog = mentionedUser;
        }

        blog.updateNoticeCheckFalse();
        blogRepository.save(blog);

        noticeService.saveReplyNotice(blog, reply);

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

        replyRepository.deleteById(replyId);
        return new Response<>(200, "댓글 삭제 완료", null);
    }

    @Transactional
    public Response<?> deleteReplyByAdmin(ReplyNoticeDeleteRequestDto replyNoticeDeleteRequestDto) {

        Reply reply = replyRepository.findById(replyNoticeDeleteRequestDto.getReplyId()).get();

        Blog blog = blogRepository.findByUserId(reply.getUser().getUserId()).get();
        blog.updateNoticeCheckFalse();

        noticeService.saveReplyDeleteNotice(reply, replyNoticeDeleteRequestDto.getReasonCategory());

        replyRepository.deleteById(reply.getId());

        return new Response<>(200, "댓글 삭제 및 알림 등록 성공", null);
    }

    public String getNickNameByUserId(String userId) {
        UserInfoResponseDto userInfo = userFeignClient.getUserInfo(userId);
        return userInfo.getNickName();
    }
}
