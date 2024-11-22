package xeat.blogservice.recommend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import xeat.blogservice.global.Response;
import xeat.blogservice.recommend.dto.RecommendRequestDto;
import xeat.blogservice.recommend.dto.RecommendResponseDto;
import xeat.blogservice.recommend.service.RecommendService;

@Tag(name = "게시글 좋아요", description = "게시글 좋아요 관련 API")
@RestController
@RequiredArgsConstructor
public class RecommendController {

    private final RecommendService recommendService;

    @Operation(summary = "게시글 좋아요 요청 및 취소", description = "게시글 좋아요 요청 시 해당 사용자가 이전에 좋아요를 눌렀는지 자체적으로 검증 후 좋아요 요청 처리 및 취소 처리")
    @PutMapping("/blog/board/article/like")
    public Response<RecommendResponseDto> recommend(@RequestHeader("UserId") String userId, @RequestBody RecommendRequestDto recommendRequestDto) {
        return recommendService.recommend(userId, recommendRequestDto);
    }
}
