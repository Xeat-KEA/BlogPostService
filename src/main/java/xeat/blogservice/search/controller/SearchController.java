package xeat.blogservice.search.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xeat.blogservice.global.Response;
import xeat.blogservice.search.dto.ArticleSearchDto;
import xeat.blogservice.search.dto.ArticleSearchResultDto;
import xeat.blogservice.search.entity.ElasticArticle;
import xeat.blogservice.search.entity.ElasticUser;
import xeat.blogservice.search.service.SearchService;

@RestController
@RequiredArgsConstructor
public class SearchController implements SearchControllerDocs{
    private final SearchService searchService;

    @GetMapping("/blog/article/search")
    public Response<Page<ArticleSearchResultDto>> allArticleSearch(@ModelAttribute ArticleSearchDto articleSearchDto) {
        return searchService.searchArticle(articleSearchDto);
    }

    @GetMapping("/blog/search")
    public Response<Page<ElasticUser>> blogSearch(@RequestParam String query, @PageableDefault(size = 20) Pageable pageable) {
        return searchService.searchBlog(query, pageable);
    }

//    @GetMapping("/blog/board/search")
//    public Response<Page<ElasticArticle>> boardArticleSearch(@ModelAttribute BoardArticleSearchDto articleSearchDto) {
//        return searchService.searchBoardArticle(articleSearchDto);
//    }
}