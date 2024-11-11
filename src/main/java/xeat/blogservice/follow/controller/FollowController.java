package xeat.blogservice.follow.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import xeat.blogservice.follow.dto.FollowRequestDto;
import xeat.blogservice.follow.dto.FollowResponseDto;
import xeat.blogservice.follow.service.FollowService;
import xeat.blogservice.global.Response;

@RestController
@RequiredArgsConstructor
@Tag(name = "팔로우", description = "블로그 팔로우 관련 API")
public class FollowController {

    private final FollowService followService;

    @Operation(summary = "블로그 팔로우 요청 및 취소", description = "이미 팔로우를 누른 사용자는 요청 시 팔로우 취소 처리됌")
    @PutMapping("/blog/home/follow/{userId}")
    public Response<FollowResponseDto> follow(@RequestHeader("UserId") String followUserId, String userId) {
        return followService.recommend(userId, followUserId);
    }
}
