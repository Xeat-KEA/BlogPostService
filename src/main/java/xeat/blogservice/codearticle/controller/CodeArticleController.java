package xeat.blogservice.codearticle.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import xeat.blogservice.codearticle.dto.CodeArticleEditRequestDto;
import xeat.blogservice.codearticle.dto.CodeArticlePostRequestDto;
import xeat.blogservice.codearticle.dto.CodeArticleResponseDto;
import xeat.blogservice.codearticle.service.CodeArticleService;
import xeat.blogservice.global.Response;

@RestController
@RequiredArgsConstructor
@RequestMapping("/blog/board/code")
public class CodeArticleController {

    private final CodeArticleService codeArticleService;

    @GetMapping("/recent")
    public Response<?> getTop5RecentCodeArticle(@RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "5") int size) {
        return codeArticleService.getTop5RecentCodeArticle(page, size);
    }

    @PostMapping
    public Response<CodeArticleResponseDto> postCodeArticle(@RequestBody CodeArticlePostRequestDto codeArticlePostRequestDto) {
        return codeArticleService.post(codeArticlePostRequestDto);
    }

    @PutMapping("/{articleId}")
    public Response<CodeArticleResponseDto> editCodeArticle(@PathVariable Long articleId, @RequestBody CodeArticleEditRequestDto codeArticleEditRequestDto) {
        return codeArticleService.edit(articleId, codeArticleEditRequestDto);
    }
}
