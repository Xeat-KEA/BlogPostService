package xeat.blogservice.codearticle.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import xeat.blogservice.codearticle.dto.*;
import xeat.blogservice.codearticle.entity.Difficulty;
import xeat.blogservice.codearticle.service.CodeArticleService;
import xeat.blogservice.global.Response;

import java.util.List;

@Tag(name = "코딩 게시글", description = "코딩 게시글 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/blog/board/code")
public class CodeArticleController {

    private final CodeArticleService codeArticleService;

    @Operation(summary = "코딩 게시글 최신순 5개 조회", description = "코딩 게시글을 최신순으로 가져와서 5개씩 페이징 처리 후 요청에 맞게 반환")
    @Parameters({
            @Parameter(name = "page", description = "조회할 페이지 번호 (0부터 시작)", example = "0", required = false),
            @Parameter(name = "size", description = "페이지당 게시글 개수", example = "5", required = false)
    })
    @GetMapping("/recent")
    public Response<CodeArticleListPageResponseDto> getTop5RecentCodeArticle(@RequestParam(defaultValue = "0") int page,
                                                                               @RequestParam(defaultValue = "5") int size) {
        return codeArticleService.getTop5RecentCodeArticle(page, size);
    }

    @Operation(summary = "코딩 게시글 작성", description = "코딩 게시글 작성시 필요한 API")
    @PostMapping
    public Response<CodeArticleResponseDto> postCodeArticle(@RequestBody CodeArticlePostRequestDto codeArticlePostRequestDto) {
        return codeArticleService.post(codeArticlePostRequestDto);
    }

    @Operation(summary = "코딩 게시글 수정", description = "코딩 게시글 수정 시 필요한 API")
    @PutMapping("/{articleId}")
    public Response<CodeArticleResponseDto> editCodeArticle(@PathVariable Long articleId, @RequestBody CodeArticleEditRequestDto codeArticleEditRequestDto) {
        return codeArticleService.edit(articleId, codeArticleEditRequestDto);
    }
}
