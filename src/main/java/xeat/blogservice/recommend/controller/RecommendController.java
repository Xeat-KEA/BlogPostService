package xeat.blogservice.recommend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import xeat.blogservice.follow.dto.FollowerListPageResponseDto;
import xeat.blogservice.global.response.Response;
import xeat.blogservice.recommend.dto.RecommendListPageResponseDto;
import xeat.blogservice.recommend.dto.RecommendResponseDto;
import xeat.blogservice.recommend.service.RecommendService;

@Tag(name = "게시글 좋아요", description = "게시글 좋아요 관련 API")
@RestController
@RequestMapping("/blog/board/article/like")
@RequiredArgsConstructor
public class RecommendController {

    private final RecommendService recommendService;

    @Operation(summary = "좋아요 사용자 목록 조회", description = "좋아요 사용자 목록을 조회할 때 필요한 API")
    @Parameters({
            @Parameter(name = "page", description = "조회할 페이지 번호 (0부터 시작)", example = "0", required = false),
            @Parameter(name = "size", description = "페이지당 좋아요 누른 사람 목록 수", example = "3", required = false)
    })
    @GetMapping("/list/{articleId}")
    public Response<RecommendListPageResponseDto> getFollowerList(@RequestParam int page,
                                                                  @RequestParam int size,
                                                                  @PathVariable Long articleId) {
        return recommendService.getRecommendList(page, size, articleId);
    }

    @Operation(summary = "게시글 좋아요 요청 및 취소", description = "게시글 좋아요 요청 시 해당 사용자가 이전에 좋아요를 눌렀는지 자체적으로 검증 후 좋아요 요청 처리 및 취소 처리")
    @PutMapping("/{articleId}")
    public Response<RecommendResponseDto> recommend(@RequestHeader("UserId") String userId, @PathVariable Long articleId) {
        return recommendService.recommend(userId, articleId);
    }
}
