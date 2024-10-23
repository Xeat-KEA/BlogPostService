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
public class CodeArticleController {

    private final CodeArticleService codeArticleService;

    @GetMapping("/blog/board/code/recent")
    public Response<?> getTop5RecentCodeArticle() {
        return codeArticleService.getTop5RecentCodeArticle();
    }

    @PostMapping("/blog/board/code")
    public Response<CodeArticleResponseDto> postCodeArticle(@RequestBody CodeArticlePostRequestDto codeArticlePostRequestDto) {
        return codeArticleService.post(codeArticlePostRequestDto);
    }

    @PutMapping("/blog/board/code/edit/{articleId}")
    public Response<CodeArticleResponseDto> editCodeArticle(@PathVariable Long articleId, @RequestBody CodeArticleEditRequestDto codeArticleEditRequestDto) {
        return codeArticleService.edit(articleId, codeArticleEditRequestDto);
    }
}
