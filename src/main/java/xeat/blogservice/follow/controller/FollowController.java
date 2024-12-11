package xeat.blogservice.follow.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import xeat.blogservice.follow.dto.FollowerListPageResponseDto;
import xeat.blogservice.follow.service.FollowService;
import xeat.blogservice.global.response.Response;

@RestController
@RequiredArgsConstructor
@RequestMapping("/blog/home/follow")
@Tag(name = "팔로우", description = "블로그 팔로우 관련 API")
public class FollowController {

    private final FollowService followService;

    @Operation(summary = "팔로우 목록 조회", description = "팔로우 목록을 조회할 때 필요한 API")
    @Parameters({
            @Parameter(name = "page", description = "조회할 페이지 번호 (0부터 시작)", example = "0", required = false),
            @Parameter(name = "size", description = "페이지당 팔로워 목록 개수", example = "3", required = false)
    })
    @GetMapping("/list/{blogId}")
    public Response<FollowerListPageResponseDto> getFollowerList(@RequestParam int page,
                                                                 @RequestParam int size,
                                                                 @PathVariable Long blogId) {
        return followService.getFollowerList(page, size, blogId);
    }

    @Operation(summary = "블로그 팔로우 요청 및 취소", description = "이미 팔로우를 누른 사용자는 요청 시 팔로우 취소 처리됌")
    @PutMapping("/{blogId}")
    public Response<?> follow(@RequestHeader("UserId") String userId, @PathVariable Long blogId) {
        return followService.recommend(blogId, userId);
    }
}
