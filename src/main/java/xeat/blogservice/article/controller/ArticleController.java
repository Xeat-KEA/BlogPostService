package xeat.blogservice.article.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import xeat.blogservice.article.dto.*;
import xeat.blogservice.article.service.ArticleService;
import xeat.blogservice.article.service.BestArticleCacheService;
import xeat.blogservice.global.response.Response;
import xeat.blogservice.notice.dto.ArticleNoticeRequestDto;


@Tag(name = "일반 게시글", description = "일반게시글 관련 API")
@RestController
@RequestMapping("/blog/board")
@RequiredArgsConstructor
@Slf4j
public class ArticleController {

    private final ArticleService articleService;
    private final BestArticleCacheService bestArticleCacheService;

    @Operation(summary = "feignClient test", description = "@FeignClient가 잘 동작하는지 테스트하기 위한 API")
    @GetMapping("/userInfo")
    public FeignClientTestDto getUserInfo(@RequestHeader("UserId") String userId) {
        return articleService.getUserInfo(userId);
    }

    @Operation(summary = "게시글 상세 조회 회원용", description = "회원이 일반 게시글 또는 코딩 게시글 하나를 클릭 하였을 때 상세 조회")
    @GetMapping("/{articleId}")
    public Response<GetArticleResponseLoginDto> getLoginUserArticle(@RequestHeader("UserId") String userId, @PathVariable Long articleId) {
        return articleService.getUserArticle(articleId, userId);
    }

    @Operation(summary = "게시글 상세 조회 비회원용", description = "비회원이 일반 게시글 또는 코딩 게시글 하나를 클릭 하였을 때 상세 조회")
    @GetMapping("/nonUser/{articleId}")
    public Response<GetArticleResponseNonUserDto> getNonUserArticle(@PathVariable Long articleId) {
        return articleService.getNonUserArticle(articleId);
    }

    @Operation(summary = "게시글 검색 조회", description = "블로그 내 게시글 목록 출력 화면에서 게시글 검색 시 필요한 API")
    @GetMapping("/search/{blogId}")
    @Parameters({
            @Parameter(name = "searchWord", description = "검색할 검색어", example = "가나다", required = false),
            @Parameter(name = "page", description = "조회할 페이지 번호 (0부터 시작)", example = "0", required = false),
            @Parameter(name = "size", description = "페이지 당 게시글 개수", example = "5", required = false)
    })
    public Response<ArticleListPageResponseDto> getArticleBySearchWord(@RequestParam String searchWord,
                                                                       @RequestParam int page,
                                                                       @RequestParam int size,
                                                                       @PathVariable Long blogId) {
        return articleService.getArticleBySearchWord(searchWord, blogId, page, size);
    }

    @Operation(summary = "블로그 내 게시글 목록 조회", description = "블로그 내에 있는 모든 게시글들을 페이징 처리하여 목록 반환")
    @GetMapping("/article/{blogId}")
    @Parameters({
            @Parameter(name = "page", description = "조회할 페이지 번호 (0부터 시작)", example = "0", required = false),
            @Parameter(name = "size", description = "페이지 당 게시글 개수", example = "5", required = false)
    })
    public Response<BlogArticleListPageResponseDto> getAllArticleByBlogId(@PathVariable Long blogId,
                                                             @RequestParam int page,
                                                             @RequestParam int size) {
        return articleService.getAllArticleByBlogId(blogId, page, size);
    }

    @Operation(summary = "특정 상위 게시판에 있는 게시글 목록 조회", description = "특정 상위 게시판에 있는 일반 게시글 또는 코딩 게시글들을 페이징 처리하여 목록 반환")
    @GetMapping("/article/parent/{parentCategoryId}")
    @Parameters({
            @Parameter(name = "blogId", description = "조회할 게시글이 위치한 블로그 Id", example = "1", required = false),
            @Parameter(name = "page", description = "조회할 페이지 번호 (0부터 시작)", example = "0", required = false),
            @Parameter(name = "size", description = "페이지당 게시글 개수", example = "5", required = false)
    })
    public Response<ArticleListPageResponseDto> getArticleByParentCategoryId(@PathVariable Long parentCategoryId,
                                                                       @RequestParam Long blogId,
                                                                       @RequestParam int page,
                                                                       @RequestParam int size) {
        return articleService.getArticleByParentCategory(page, size, blogId, parentCategoryId);
    }

    @Operation(summary = "특정 하위 게시판에 있는 게시글 목록 조회", description = "특정 하위 게시판에 있는 일반 게시글 또는 코딩 게시글들을 페이징 처리하여 목록 반환")
    @GetMapping("/article/child/{childCategoryId}")
    @Parameters({
            @Parameter(name = "blogId", description = "조회할 게시글이 위치한 블로그 Id", example = "1", required = false),
            @Parameter(name = "page", description = "조회할 페이지 번호 (0부터 시작)", example = "0", required = false),
            @Parameter(name = "size", description = "페이지당 게시글 개수", example = "5", required = false)
    })
    public Response<ArticleListPageResponseDto> getArticleByChildCategoryId(@PathVariable Long childCategoryId,
                                                              @RequestParam Long blogId,
                                                              @RequestParam int page,
                                                              @RequestParam int size) {
        return articleService.getArticleByChildCategory(page, size, blogId, childCategoryId);
    }

    @Operation(summary = "좋아요 개수 많은 순으로 게시글 3개 조회", description = "전체 게시글 중 좋아요 수가 많은 게시글 3개를 조회")
    @GetMapping("/all/best")
    public Response<BestArticleResponseDto> getBestArticle() {
        return bestArticleCacheService.getBestArticle();
    }

    @Operation(summary = "전체 게시글 최신순 5개 조회", description = "전체 게시글 중 최신글 5개를 조회")
    @GetMapping("/all/recent")
    @Parameters({
            @Parameter(name = "page", description = "조회할 페이지 번호 (0부터 시작)", example = "0", required = false),
            @Parameter(name = "size", description = "페이지당 게시글 개수", example = "5", required = false)
    })
    public Response<ArticleListPageResponseDto> getTop3RecentAllArticle(@RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "5") int size) {
        return articleService.getTop3RecentAllArticle(page, size);
    }

    @Operation(summary = "일반 게시글 최신순 3개 조회", description = "일반 게시글 중 최신글 3개를 조회")
    @GetMapping("/article/recent")
    @Parameters({
            @Parameter(name = "page", description = "조회할 페이지 번호 (0부터 시작)", example = "0", required = false),
            @Parameter(name = "size", description = "페이지당 게시글 개수", example = "3", required = false)
    })
    public Response<ArticleListPageResponseDto> getTop3RecentArticle(@RequestParam(defaultValue = "0") int page,
                                                                       @RequestParam(defaultValue = "3") int size) {
        return articleService.getTop3RecentArticle(page, size);
    }

    @Operation(summary = "게시글 수정 페이지 조회", description = "사용자가 게시글 수정 페이지를 들어갈때 기존 게시글 조회에 필요한 API")
    @GetMapping("/article/edit/{articleId}")
    public Response<ArticleEditResponseDto> getEditArticle(@PathVariable Long articleId) throws Exception {
        return articleService.getArticleEdit(articleId);
    }

    @Operation(summary = "게시글 비밀번호 일치 여부 조회", description = "비밀글을 조회하기 위해 입력한 비밀번호가 맞는지 확인을 위한 API")
    @Parameter(name = "password", description = "사용자가 입력한 비밀번호", example = "1234", required = true)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시글 비밀번호가 일치합니다"),
            @ApiResponse(responseCode = "404", description = "게시글 비밀번호가 일치하지 않습니다")
    })
    @GetMapping("/password/{articleId}")
    public Response<?> checkPassword(@PathVariable Long articleId, @RequestParam String password) {
        if (articleService.passwordCheck(articleId, password)) {
            return new Response<>(200, "게시글 비밀번호가 일치합니다", null);
        }
        else {
            return new Response<>(404, "게시글 비밀번호가 일치하지 않습니다", null);
        }
    }

    @Operation(summary = "일반 게시글 작성", description = "일반 게시글 작성(코딩 게시글 작성 API는 별도로 있음)")
    @PostMapping("/article")
    public Response<ArticlePostResponseDto> postArticle(@RequestHeader("UserId") String userId,
                                                        @RequestBody ArticlePostRequestDto articlePostRequestDto) throws Exception{
        return articleService.post(userId, articlePostRequestDto);
    }

    @Operation(summary = "일반 게시글 수정", description = "일반 게시글 수정(코딩 게시글 수정 API는 별도로 있음)")
    @PutMapping("/article/{articleId}")
    public Response<ArticlePostResponseDto> editArticle(@PathVariable Long articleId, @RequestBody ArticleEditRequestDto articleEditRequestDto) throws Exception {
        return articleService.edit(articleId, articleEditRequestDto);
    }

    @Operation(summary = "게시글 블라인드 처리 및 해제", description = "관리자가 게시글을 블라인드 처리하거나 해제할 때 필요한 API")
    @PutMapping("/article/blind")
    public Response<ArticlePostResponseDto> editBlind(@RequestBody ArticleNoticeRequestDto articleNoticeRequestDto) {
        return articleService.editBlind(articleNoticeRequestDto);
    }

    @Operation(summary = "게시글 삭제", description = "게시글 삭제 처리")
    @DeleteMapping("/article/{articleId}")
    public Response<?> deleteArticle(@PathVariable Long articleId) {
        return articleService.delete(articleId);
    }

}
