package xeat.blogservice.recommend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import xeat.blogservice.global.Response;
import xeat.blogservice.recommend.dto.RecommendRequestDto;
import xeat.blogservice.recommend.dto.RecommendResponseDto;
import xeat.blogservice.recommend.service.RecommendService;

@RestController
@RequiredArgsConstructor
public class RecommendController {

    private final RecommendService recommendService;

    @PostMapping("/blog/board/article/like")
    public Response<?> recommend(@RequestBody RecommendRequestDto recommendRequestDto) {
        return recommendService.recommend(recommendRequestDto);
    }
}
