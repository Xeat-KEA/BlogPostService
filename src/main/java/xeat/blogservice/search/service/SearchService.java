package xeat.blogservice.search.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import xeat.blogservice.global.Response;
import xeat.blogservice.search.dto.ArticleSearchDto;
import xeat.blogservice.search.entity.ElasticArticle;
import xeat.blogservice.search.repository.ElasticArticleRepository;

import static org.springframework.data.domain.Sort.Direction.DESC;

@Service
@RequiredArgsConstructor
public class SearchService {
    private final ElasticArticleRepository elasticArticleRepository;

    public Response<Page<ElasticArticle>> searchArticle(ArticleSearchDto articleSearchDto) {
        if (articleSearchDto.getType().equals("normal")) {
            return Response.success(elasticArticleRepository.findArticleByQuery(articleSearchDto.getQuery(), getPageable(articleSearchDto))
                    .map(elasticArticle -> {
                        elasticArticle.highlighting(articleSearchDto.getQuery());
                        return elasticArticle;
                    }));
        } else if (articleSearchDto.getType().equals("code")) {
            return Response.success(elasticArticleRepository.findCodeArticleByQuery(articleSearchDto.getQuery(), getPageable(articleSearchDto))
                    .map(elasticArticle -> {
                        elasticArticle.highlighting(articleSearchDto.getQuery());
                        return elasticArticle;
                    }));
        }
        return Response.success(elasticArticleRepository.findAllByQuery(articleSearchDto.getQuery(), getPageable(articleSearchDto))
                .map(elasticArticle -> {
                    elasticArticle.highlighting(articleSearchDto.getQuery());
                    return elasticArticle;
                }));
    }

    private Pageable getPageable(ArticleSearchDto articleSearchDto) {
        if (articleSearchDto.getSort().equals("latest")) {
            return PageRequest.of(articleSearchDto.getPage(), 10, DESC, "created_date");
        }
        return PageRequest.of(articleSearchDto.getPage(), 10);
    }
}