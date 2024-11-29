package xeat.blogservice.recommend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xeat.blogservice.article.entity.Article;
import xeat.blogservice.article.repository.ArticleRepository;
import xeat.blogservice.blog.entity.Blog;
import xeat.blogservice.blog.repository.BlogRepository;
import xeat.blogservice.global.response.Response;
import xeat.blogservice.recommend.dto.RecommendResponseDto;
import xeat.blogservice.recommend.entity.Recommend;
import xeat.blogservice.recommend.repository.RecommendRepository;

@Service
@RequiredArgsConstructor
public class RecommendService {

    private final RecommendRepository recommendRepository;
    private final BlogRepository blogRepository;
    private final ArticleRepository articleRepository;

    @Transactional
    public Response<RecommendResponseDto> recommend(String userId, Long articleId) {

        Article article = articleRepository.findById(articleId).get();
        Blog user = blogRepository.findByUserId(userId).get();

        if (!recommendRepository.existsByArticleAndUser(article, user)) {

            Recommend recommend = Recommend.builder()
                    .article(articleRepository.findById(articleId).get())
                    .user(blogRepository.findByUserId(userId).get())
                    .build();

            recommendRepository.save(recommend);

            article.plusLikeCount();
            articleRepository.save(article);

            return new Response<>(200, "게시글 좋아요 요청 성공", RecommendResponseDto.toDto(article));
        }

        else {
            recommendRepository.delete(recommendRepository.findByArticleAndUser(article, user).get());
            article.minusLikeCount();
            articleRepository.save(article);
            return new Response<>(200, "게시글 좋아요 취소 성공", RecommendResponseDto.toDto(article));
        }
    }
}
