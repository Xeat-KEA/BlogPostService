package xeat.blogservice.article.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xeat.blogservice.article.dto.ArticleListResponseDto;
import xeat.blogservice.article.dto.BestArticleResponseDto;
import xeat.blogservice.article.entity.Article;
import xeat.blogservice.article.repository.ArticleRepository;
import xeat.blogservice.codearticle.dto.CodeArticleListResponseDto;
import xeat.blogservice.codearticle.entity.CodeArticle;
import xeat.blogservice.codearticle.repository.CodeArticleRepository;
import xeat.blogservice.global.Response;
import xeat.blogservice.global.ResponseDto;
import xeat.blogservice.global.feignclient.UserFeignClient;
import xeat.blogservice.global.feignclient.UserInfoResponseDto;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BestArticleCacheService {

    private static final String BEST_ARTICLES_KEY = "bestArticles";

    private final RedisTemplate<String, String> redisTemplate;
    private final ArticleRepository articleRepository;
    private final CodeArticleRepository codeArticleRepository;
    private final UserFeignClient userFeignClient;

    @Scheduled(cron = "0 */10 * * * *") // 매시간 정각마다 로직 수행
    public void updateBestArticles() {
        LocalDateTime startOfWeek = getThisWeekMonday(); // 이번주 월요일
        Pageable pageable = PageRequest.of(0, 3); // 상위 3개만 가져오기
        List<Article> bestArticles = articleRepository.findBestArticleList(pageable, startOfWeek);

        redisTemplate.delete(BEST_ARTICLES_KEY); // 기존 캐시 삭제

        for(Article article : bestArticles) {
            redisTemplate.opsForZSet().add(BEST_ARTICLES_KEY,
                    article.getId().toString(),
                    article.getLikeCount());
            log.info("Article ID = {}, Article LikeCount={}", article.getId(), article.getLikeCount());
        }
    }

    @Transactional
    public Response<BestArticleResponseDto> getBestArticle() {
        Set<String> articleIds = redisTemplate.opsForZSet().reverseRange(BEST_ARTICLES_KEY, 0, 2);// score가 높은 순으로 3개

        //Redis에서 가져온 ID들로 DB에서 게시글 정보를 조회
        List<Long> articleIdList = articleIds.stream()
                .map(Long::parseLong)
                .toList();

        List<ResponseDto> articleList = new ArrayList<>();

        for (Long articleId : articleIdList) {
            Article article = articleRepository.findById(articleId).get();
            UserInfoResponseDto userInfo = userFeignClient.getUserInfo(article.getBlog().getUserId());
            if (codeArticleRepository.existsByArticleId(articleId)) {
                CodeArticle codeArticle = codeArticleRepository.findByArticleId(articleId).get();
                articleList.add(CodeArticleListResponseDto.toDto(codeArticle, userInfo));
            }
            else {
                articleList.add(ArticleListResponseDto.toDto(article, userInfo));
            }
        }

        return Response.success(BestArticleResponseDto.toDto(articleList));
    }


    // 월요일 06:00의 날짜를 반환하는 메서드
    private LocalDateTime getThisWeekMonday() {
        return LocalDateTime.now()
                .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))  // 이번 주 월요일
                .withHour(6)  // 오전 6시
                .withMinute(0)
                .withSecond(0)
                .withNano(0);
    }
}
