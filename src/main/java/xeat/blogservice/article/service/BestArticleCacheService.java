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
import xeat.blogservice.global.response.Response;
import xeat.blogservice.global.response.ResponseDto;
import xeat.blogservice.global.feignclient.UserFeignClient;
import xeat.blogservice.global.feignclient.UserInfoResponseDto;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BestArticleCacheService {

    private static final String BEST_ARTICLES_KEY = "bestArticles";

    private final RedisTemplate<String, String> redisTemplate;
    private final ArticleRepository articleRepository;
    private final CodeArticleRepository codeArticleRepository;
    private final UserFeignClient userFeignClient;

    @Scheduled(cron = "0 */10 * * * *") // 10분마다 로직 실행
    public void updateBestArticles() {
        LocalDateTime startOfWeek = getThisWeekMonday(); // 이번주 월요일
        Pageable pageable = PageRequest.of(0, 3); // 상위 3개만 가져오기
        List<Article> bestArticles = articleRepository.findBestArticleList(pageable, startOfWeek);

        redisTemplate.delete(BEST_ARTICLES_KEY); // 기존 캐시 삭제

        for(Article article : bestArticles) {
            redisTemplate.opsForList().rightPush(BEST_ARTICLES_KEY, article.getId().toString());
            log.info("Article ID = {}, Article LikeCount = {}, Article ViewCount = {}", article.getId(), article.getLikeCount(), article.getViewCount());
        }
    }

    @Transactional
    public Response<BestArticleResponseDto> getBestArticle() {
        List<String> articleIds = redisTemplate.opsForList().range(BEST_ARTICLES_KEY, 0, -1);// score가 높은 순으로 3개

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

    public Boolean deleteArticle(Long articleId) {
        List<String> cachedArticleIds = redisTemplate.opsForList().range(BEST_ARTICLES_KEY, 0, -1);
        if (cachedArticleIds != null && cachedArticleIds.contains(String.valueOf(articleId))) {
            updateBestArticles(); // 캐시 업데이트
            return true;
        }
        else {
            return false;
        }
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
