package xeat.blogservice.article.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xeat.blogservice.article.dto.*;
import xeat.blogservice.article.entity.Article;
import xeat.blogservice.article.repository.ArticleRepository;
import xeat.blogservice.blog.repository.BlogRepository;
import xeat.blogservice.childcategory.entity.ChildCategory;
import xeat.blogservice.childcategory.repository.ChildCategoryRepository;
import xeat.blogservice.codearticle.dto.GetCodeArticleResponseDto;
import xeat.blogservice.codearticle.entity.CodeArticle;
import xeat.blogservice.codearticle.repository.CodeArticleRepository;
import xeat.blogservice.global.Response;
import xeat.blogservice.reply.dto.ArticleReplyResponseDto;
import xeat.blogservice.reply.dto.ChildReplyResponseDto;
import xeat.blogservice.reply.entity.Reply;
import xeat.blogservice.reply.repository.ReplyRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ArticleService {

    private final BlogRepository blogRepository;
    private final ChildCategoryRepository childCategoryRepository;
    private final ArticleRepository articleRepository;
    private final CodeArticleRepository codeArticleRepository;
    private final ReplyRepository replyRepository;

    @Transactional
    public Response<?> getArticle(Long articleId) {
        Article article = articleRepository.findById(articleId).get();
        List<Reply> replyList = replyRepository.findParentReplies(articleId);
        for (Reply reply : replyList) {
            System.out.println(reply.getContent());
        }

        List<ArticleReplyResponseDto> articleReplyResponseDtoList = new ArrayList<>();

        replyList.forEach(s -> articleReplyResponseDtoList.add(ArticleReplyResponseDto.toDto(
                s, makeChildListDto(s)
        )));

        // 코딩테스트 게시글일 경우 GetCodeArticleResponseDto에 값을 담아서 반환하도록 처리
        if (codeArticleRepository.existsByArticleId(articleId)) {
            CodeArticle codeArticle = codeArticleRepository.findByArticleId(articleId).get();
            return Response.success(GetCodeArticleResponseDto.toDto(article, codeArticle, articleReplyResponseDtoList));
        }
        else {
            return Response.success(GetArticleResponseDto.toDto(article, articleReplyResponseDtoList));

        }
    }

    @Transactional
    public Response<?> getTop5RecentArticle() {
        List<Article> recentArticle = articleRepository.findRecentArticle();
        List<ArticleRecentResponseDto> recentArticleListDto = new ArrayList<>();

        recentArticle.forEach(s -> recentArticleListDto.add(ArticleRecentResponseDto.toDto(s)));
        return Response.success(recentArticleListDto);
    }

    @Transactional
    public Response<ArticlePostResponseDto> post(ArticlePostRequestDto articlePostRequestDto) {

        Article article = Article.builder()
                .blog(blogRepository.findById(articlePostRequestDto.getBlogId()).get())
                .childCategory(childCategoryRepository.findById(articlePostRequestDto.getChildCategoryId()).get())
                .title(articlePostRequestDto.getTitle())
                .content(articlePostRequestDto.getContent())
                .isSecret(articlePostRequestDto.getIsSecret())
                .password(articlePostRequestDto.getPassword())
                .build();
        return Response.success(ArticlePostResponseDto.toDto(articleRepository.save(article)));
    }

    @Transactional
    public Response<Article> edit(Long articleId, ArticleEditRequestDto articleEditRequestDto) {
        Article article = articleRepository.findById(articleId).get();
        ChildCategory childCategory = childCategoryRepository.findById(articleEditRequestDto.getChildCategoryId()).get();

        article.editArticle(articleEditRequestDto, childCategory);
        return Response.success(articleRepository.save(article));
    }

    @Transactional
    public Response<?> delete(Long articleId) {
        articleRepository.deleteById(articleId);

        if (codeArticleRepository.existsByArticleId(articleId)) {
            codeArticleRepository.delete(codeArticleRepository.findByArticleId(articleId).get());
        }
        return new Response<>(200, "게시글 삭제 완료", null);
    }

    // 부모 댓글에 달린 모든 대댓글 dto에 추가하는 method
    public List<ChildReplyResponseDto> makeChildListDto(Reply reply) {
        List<Reply> childReplyList = replyRepository.findAllByParentReplyId(reply.getId());
        List<ChildReplyResponseDto> childReplyResponseDtoList = new ArrayList<>();

        childReplyList.forEach(s -> childReplyResponseDtoList.add(ChildReplyResponseDto.toDto(s)));
        return childReplyResponseDtoList;
    }

}