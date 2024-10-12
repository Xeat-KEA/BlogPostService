package xeat.blogservice.codearticle.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import xeat.blogservice.codearticle.dto.CodeArticlePostRequestDto;
import xeat.blogservice.codearticle.service.CodeArticleService;
import xeat.blogservice.global.Response;

@RestController
@RequiredArgsConstructor
public class CodeArticleController {

    private final CodeArticleService codeArticleService;

    @PostMapping("/blog/board/code")
    public Response<CodeArticlePostRequestDto> postCodeArticle(@RequestBody CodeArticlePostRequestDto codeArticlePostRequestDto) {
        return codeArticleService.post(codeArticlePostRequestDto);
    }
}
