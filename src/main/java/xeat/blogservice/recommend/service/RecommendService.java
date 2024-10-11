package xeat.blogservice.recommend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xeat.blogservice.article.entity.Article;
import xeat.blogservice.article.repository.ArticleRepository;
import xeat.blogservice.blog.repository.BlogRepository;
import xeat.blogservice.global.Response;
import xeat.blogservice.recommend.dto.RecommendRequestDto;
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
    public Response<?> recommend(RecommendRequestDto recommendRequestDto) {

        Long articleId = recommendRequestDto.getArticleId();
        Long userId = recommendRequestDto.getUserId();

        Article article = articleRepository.findById(articleId).get();

        if (!recommendRepository.existsByArticleIdAndUserId(articleId, userId)) {

            Recommend recommend = Recommend.builder()
                    .article(articleRepository.findById(articleId).get())
                    .user(blogRepository.findByUserId(userId).get())
                    .build();

            recommendRepository.save(recommend);

            Article updateArticle = article.toBuilder()
                    .likeCount(article.getLikeCount() + 1)
                    .build();
            articleRepository.save(updateArticle);

            return new Response<>(200, "게시글 좋아요 요청 성공", RecommendResponseDto.toDto(updateArticle));
        }

        else {
            recommendRepository.delete(recommendRepository.findByArticleIdAndUserId(articleId, userId).get());
            Article updateArticle = article.toBuilder()
                    .likeCount(article.getLikeCount() - 1)
                    .build();
            articleRepository.save(updateArticle);
            return new Response<>(200, "게시글 좋아요 취소 성공", RecommendResponseDto.toDto(updateArticle));
        }
    }
}
