package xeat.blogservice.follow.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import xeat.blogservice.follow.dto.FollowRequestDto;
import xeat.blogservice.follow.service.FollowService;
import xeat.blogservice.global.Response;

@RestController
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    @PostMapping("/blog/home/follow")
    public Response<?> follow(@RequestBody FollowRequestDto followRequestDto) {
        return followService.recommend(followRequestDto);
    }
}
