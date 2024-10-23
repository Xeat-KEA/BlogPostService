package xeat.blogservice.article.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import xeat.blogservice.article.dto.ArticleEditRequestDto;
import xeat.blogservice.article.dto.ArticlePostRequestDto;
import xeat.blogservice.article.entity.Article;
import xeat.blogservice.article.service.ArticleService;
import xeat.blogservice.global.Response;

@RestController
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping("/blog/board/{articleId}")
    public Response<?> getArticle(@PathVariable Long articleId) {
        return articleService.getArticle(articleId);
    }

    @PostMapping("/blog/board/article")
    public Response<Article> postArticle(@RequestBody ArticlePostRequestDto articlePostRequestDto) {
        return articleService.post(articlePostRequestDto);
    }

    @PutMapping("/blog/board/article/edit/{articleId}")
    public Response<Article> editArticle(@PathVariable Long articleId, @RequestBody ArticleEditRequestDto articleEditRequestDto) {
        return articleService.edit(articleId, articleEditRequestDto);
    }

    @DeleteMapping("/blog/board/article/delete/{articleId}")
    public Response<?> deleteArticle(@PathVariable Long articleId) {
        return articleService.delete(articleId);
    }
}