package xeat.blogservice.article.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import xeat.blogservice.article.dto.*;
import xeat.blogservice.article.entity.Article;
import xeat.blogservice.article.service.ArticleService;
import xeat.blogservice.global.Response;


@Tag(name = "일반 게시글", description = "일반게시글 관련 API")
@RestController
@RequestMapping("/blog/board")
@RequiredArgsConstructor
@Slf4j
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping("/userInfo")
    public void getUserInfo(@RequestHeader("UserId") String userId) {
        log.info("userId = {}", userId);
    }

    @Operation(summary = "게시글 상세 조회", description = "일반 게시글 또는 코딩 게시글 하나를 클릭 하였을 때 상세 조회")
    @GetMapping("/{articleId}")
    public Response<GetArticleResponseDto> getArticle(@PathVariable Long articleId) {
        return articleService.getArticle(articleId);
    }

    @Operation(summary = "블로그 내 게시글 목록 조회", description = "블로그 내에 있는 모든 게시글들을 페이징 처리하여 목록 반환")
    @GetMapping("blog/{blogId}")
    @Parameters({
            @Parameter(name = "page", description = "조회할 페이지 번호 (0부터 시작)", example = "0", required = false),
            @Parameter(name = "size", description = "페이지 당 게시글 개수", example = "5", required = false)
    })
    public Response<ArticleListPageResponseDto> getAllArticleByBlogId(@PathVariable Long blogId,
                                                             @RequestParam int page,
                                                             @RequestParam int size) {
        return articleService.getAllArticleByBlogId(blogId, page, size);
    }

    @Operation(summary = "특정 게시판에 있는 게시글 목록 조회", description = "특정 게시판에 있는 일반 게시글 또는 코딩 게시글들을 페이징 처리하여 목록 반환")
    @GetMapping("category/{childCategoryId}")
    @Parameters({
            @Parameter(name = "page", description = "조회할 페이지 번호 (0부터 시작)", example = "0", required = false),
            @Parameter(name = "size", description = "페이지당 게시글 개수", example = "5", required = false)
    })
    public Response<ArticleListPageResponseDto> getArticleByCategoryId(@PathVariable Long childCategoryId,
                                                              @RequestParam Long blogId,
                                                              @RequestParam int page,
                                                              @RequestParam int size) {
        return articleService.getArticleByChildCategory(page, size, blogId, childCategoryId);
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
    public Response<ArticleListPageResponseDto> getTop5RecentAllArticle(@RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "5") int size) {
        return articleService.getTop5RecentAllArticle(page, size);
    }

    @Operation(summary = "일반 게시글 최신순 5개 조회", description = "일반 게시글 중 최신글 5개를 조회")
    @GetMapping("/article/recent")
    @Parameters({
            @Parameter(name = "page", description = "조회할 페이지 번호 (0부터 시작)", example = "0", required = false),
            @Parameter(name = "size", description = "페이지당 게시글 개수", example = "5", required = false)
    })
    public Response<ArticleListPageResponseDto> getTop5RecentArticle(@RequestParam(defaultValue = "0") int page,
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
