package xeat.blogservice.search.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;
import xeat.blogservice.global.Response;
import xeat.blogservice.search.dto.ArticleSearchDto;
import xeat.blogservice.search.entity.ElasticArticle;
import xeat.blogservice.search.service.SearchService;

@RestController
@RequiredArgsConstructor
public class SearchController {
    private final SearchService searchService;

    @GetMapping("/blog/board/search")
    public Response<Page<ElasticArticle>> articleSearch(@ModelAttribute ArticleSearchDto articleSearchDto) {
        return searchService.searchArticle(articleSearchDto);
    }

}
