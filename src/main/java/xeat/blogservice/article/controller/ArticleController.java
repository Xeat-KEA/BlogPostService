package xeat.blogservice.article.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import xeat.blogservice.article.dto.ArticlePostRequestDto;
import xeat.blogservice.article.entity.Article;
import xeat.blogservice.article.service.ArticleService;
import xeat.blogservice.global.Response;

@RestController
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping("/blog/board/article")
    public Response<Article> postArticle(@RequestBody ArticlePostRequestDto articlePostRequestDto) {
        return articleService.post(articlePostRequestDto);
    }
}
