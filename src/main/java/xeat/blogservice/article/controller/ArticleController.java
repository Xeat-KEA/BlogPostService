package xeat.blogservice.article.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import xeat.blogservice.article.dto.*;
import xeat.blogservice.article.entity.Article;
import xeat.blogservice.article.service.ArticleService;
import xeat.blogservice.global.Response;

import java.util.List;

@Tag(name = "일반 게시글", description = "일반게시글 관련 API")
@RestController
@RequestMapping("/blog/board")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @Operation(summary = "일반 게시글 상세 조회", description = "일반 게시글 하나를 클릭 하였을때 상세 조회")
    @GetMapping("/{articleId}")
    public Response<GetArticleResponseDto> getArticle(@PathVariable Long articleId) {
        return articleService.getArticle(articleId);
    }

    @Operation(summary = "좋아요 개수 많은 순으로 게시글 5개 조회", description = "전체 게시글 중 좋아요 수가 많은 게시글 5개를 조회")
    @GetMapping("all/like")
    public Response<?> getTop5LikeCountAllArticle() {
        return articleService.getTop5LikeCountArticle();
    }

    @Operation(summary = "전체 게시글 최신순 5개 조회", description = "전체 게시글 중 최신글 5개를 조회")
    @GetMapping("/all/recent")
    @Parameters({
            @Parameter(name = "page", description = "조회할 페이지 번호 (0부터 시작)", example = "0", required = false),
            @Parameter(name = "size", description = "페이지당 게시글 개수", example = "5", required = false)
    })
    public Response<?> getTop5RecentAllArticle(@RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "5") int size) {
        return articleService.getTop5RecentAllArticle(page, size);
    }

    @Operation(summary = "일반 게시글 최신순 5개 조회", description = "일반 게시글 중 최신글 5개를 조회")
    @GetMapping("/article/recent")
    @Parameters({
            @Parameter(name = "page", description = "조회할 페이지 번호 (0부터 시작)", example = "0", required = false),
            @Parameter(name = "size", description = "페이지당 게시글 개수", example = "5", required = false)
    })
    public Response<List<ArticleRecentResponseDto>> getTop5RecentArticle(@RequestParam(defaultValue = "0") int page,
                                                                         @RequestParam(defaultValue = "5") int size) {
        return articleService.getTop5RecentArticle(page, size);
    }

    @Operation(summary = "일반 게시글 작성", description = "일반 게시글 작성(코딩 게시글 작성 API는 별도로 있음)")
    @PostMapping("/article")
    public Response<ArticlePostResponseDto> postArticle(@RequestBody ArticlePostRequestDto articlePostRequestDto) {
        return articleService.post(articlePostRequestDto);
    }

    @Operation(summary = "일반 게시글 수정", description = "일반 게시글 수정(코딩 게시글 수정 API는 별도로 있음)")
    @PutMapping("/article/edit/{articleId}")
    public Response<Article> editArticle(@PathVariable Long articleId, @RequestBody ArticleEditRequestDto articleEditRequestDto) {
        return articleService.edit(articleId, articleEditRequestDto);
    }

    @Operation(summary = "게시글 삭제", description = "게시글 삭제 처리")
    @DeleteMapping("/article/{articleId}")
    public Response<?> deleteArticle(@PathVariable Long articleId) {
        return articleService.delete(articleId);
    }
}
