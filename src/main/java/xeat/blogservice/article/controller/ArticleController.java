package xeat.blogservice.article.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import xeat.blogservice.article.dto.ArticleEditRequestDto;
import xeat.blogservice.article.dto.ArticlePostRequestDto;
import xeat.blogservice.article.dto.ArticlePostResponseDto;
import xeat.blogservice.article.entity.Article;
import xeat.blogservice.article.service.ArticleService;
import xeat.blogservice.global.Response;

@RestController
@RequestMapping("/blog/board")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping("/{articleId}")
    public Response<?> getArticle(@PathVariable Long articleId) {
        return articleService.getArticle(articleId);
    }

    @GetMapping("all/like")
    public Response<?> getTop5LikeCountAllArticle() {
        return articleService.getTop5LikeCountArticle();
    }

    @GetMapping("/all/recent")
    public Response<?> getTop5RecentAllArticle() {
        return articleService.getTop5RecentAllArticle();
    }

    @GetMapping("/article/recent")
    public Response<?> getTop5RecentArticle() {
        return articleService.getTop5RecentArticle();
    }

    @PostMapping("/article")
    public Response<ArticlePostResponseDto> postArticle(@RequestBody ArticlePostRequestDto articlePostRequestDto) {
        return articleService.post(articlePostRequestDto);
    }

    @PutMapping("/article/edit/{articleId}")
    public Response<Article> editArticle(@PathVariable Long articleId, @RequestBody ArticleEditRequestDto articleEditRequestDto) {
        return articleService.edit(articleId, articleEditRequestDto);
    }

    @DeleteMapping("/article/delete/{articleId}")
    public Response<?> deleteArticle(@PathVariable Long articleId) {
        return articleService.delete(articleId);
    }
}