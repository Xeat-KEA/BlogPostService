package xeat.blogservice.search.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import xeat.blogservice.article.entity.Article;
import xeat.blogservice.article.repository.ArticleRepository;
import xeat.blogservice.global.response.Response;
import xeat.blogservice.search.dto.ArticleSearchDto;
import xeat.blogservice.search.dto.ArticleSearchResultDto;
import xeat.blogservice.search.entity.ElasticUser;
import xeat.blogservice.search.repository.ElasticArticleRepository;
import xeat.blogservice.search.repository.ElasticUserRepository;

import static org.springframework.data.domain.Sort.Direction.DESC;

@Service
@RequiredArgsConstructor
public class SearchService {
    private final ElasticArticleRepository elasticArticleRepository;
    private final ElasticUserRepository elasticuserRepository;
    private final ArticleRepository articleRepository;

    public Response<Page<ArticleSearchResultDto>> searchArticle(ArticleSearchDto articleSearchDto) {
        if (articleSearchDto.getType().equals("normal")) {
            return Response.success(elasticArticleRepository.findArticleByQuery(articleSearchDto.getQuery(), getPageable(articleSearchDto))
                    .map(search -> {
                        Article article = articleRepository.findById(Long.valueOf(search.getContent().getArticleId())).get();
                        return new ArticleSearchResultDto(search.getContent(), search.getHighlightFields(), article.getIsBlind(), article.getIsSecret());
                    }));
        } else if (articleSearchDto.getType().equals("code")) {
            ///검색어가 숫자인지(문제 번호인지) 검사
            if (articleSearchDto.getQuery().chars().allMatch(Character::isDigit)) {
                return Response.success(elasticArticleRepository.findCodeArticleByCodeNum(articleSearchDto.getQuery(), getPageable(articleSearchDto))
                        .map(search -> {
                            Article article = articleRepository.findById(Long.valueOf(search.getContent().getArticleId())).get();
                            return new ArticleSearchResultDto(search.getContent(), search.getHighlightFields(), article.getIsBlind(), article.getIsSecret());
                        }));
            }
            return Response.success(elasticArticleRepository.findCodeArticleByQuery(articleSearchDto.getQuery(), getPageable(articleSearchDto))
                    .map(search -> {
                        Article article = articleRepository.findById(Long.valueOf(search.getContent().getArticleId())).get();
                        return new ArticleSearchResultDto(search.getContent(), search.getHighlightFields(), article.getIsBlind(), article.getIsSecret());
                    }));
        }
        ///검색어가 숫자인지(문제 번호인지) 검사
        if (articleSearchDto.getQuery().chars().allMatch(Character::isDigit)) {
            return Response.success(elasticArticleRepository.findByCodeNum(articleSearchDto.getQuery(), getPageable(articleSearchDto))
                    .map(search -> {
                        Article article = articleRepository.findById(Long.valueOf(search.getContent().getArticleId())).get();
                        return new ArticleSearchResultDto(search.getContent(), search.getHighlightFields(), article.getIsBlind(), article.getIsSecret());
                    }));
        }
        return Response.success(elasticArticleRepository.findAllByQuery(articleSearchDto.getQuery(), getPageable(articleSearchDto))
                .map(search -> {
                    Article article = articleRepository.findById(Long.valueOf(search.getContent().getArticleId())).get();
                    return new ArticleSearchResultDto(search.getContent(), search.getHighlightFields(), article.getIsBlind(), article.getIsSecret());
                }));
    }

    private Pageable getPageable(ArticleSearchDto articleSearchDto) {
        if (articleSearchDto.getSort().equals("latest")) {
            return PageRequest.of(articleSearchDto.getPage(), 10, DESC, "created_date");
        }
        return PageRequest.of(articleSearchDto.getPage(), 10);
    }

    public Response<Page<ElasticUser>> searchBlog(String query, Pageable pageable) {
        return Response.success(elasticuserRepository.findAllByQuery(query, pageable));
    }

//    public Response<Page<ElasticArticle>> searchBoardArticle(BoardArticleSearchDto articleSearchDto) {
//        if (articleSearchDto.getType().equals("normal")) {
//            return Response.success(elasticArticleRepository.findParentByQuery(articleSearchDto.getQuery(),
//                            articleSearchDto.getNickname(), getPageable(articleSearchDto)).
//
//        } else if (articleSearchDto.getType().equals("code")) {
//            return Response.success(elasticArticleRepository.findChildByQuery(articleSearchDto.getQuery(),
//                            articleSearchDto.getNickname(), getPageable(articleSearchDto))
//                    .map(elasticArticle -> {
//                        elasticArticle.highlighting(articleSearchDto.getQuery());
//                        return elasticArticle;
//                    }));
//        }
//        return Response.success(elasticArticleRepository.findAllByQuery(articleSearchDto.getQuery(),
//                        articleSearchDto.getNickname(), getPageable(articleSearchDto))
//                .map(elasticArticle -> {
//                    elasticArticle.highlighting(articleSearchDto.getQuery());
//                    return elasticArticle;
//                }));
//    }
    //    public Response<Page<ArticleSearchResultDto>> searchArticle(ArticleSearchDto articleSearchDto) {
//        Pageable pageable = getPageable(articleSearchDto);
//        return Response.success(SearchHitSupport.searchPageFor(operations.search(NativeQuery.builder()
//                .withQuery(q -> q.bool(b -> b.should(s -> s.match(m -> m
//                        .field("title").field("content").query(articleSearchDto.getQuery())))))
//                .withPageable(pageable)
//                .withHighlightQuery(new HighlightQuery(new Highlight(HighlightParameters.builder()
//                        .withPreTags("<b>")
//                        .withPostTags("</b>")
//                        .build(),
//                        List.of(new HighlightField("title"),
//                                new HighlightField("content")
//                        )
//                ), ElasticArticle.class))
//                .build(), ElasticArticle.class), pageable).map(articleResult -> new ArticleSearchResultDto(articleResult.getContent(), articleResult.getHighlightFields())));
//    }
}
