package xeat.blogservice.global.userclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "userservice-dev", url = "https://172.16.211.57:8081")
public interface UserFeignClient {

    @GetMapping(value = "/users/userInfo")
    UserInfoResponseDto getUserInfo(@RequestHeader("UserId") String userId);

    @GetMapping(value = "/users/statistics")
    UserRankResponseDto getUserRank(@RequestHeader("UserId") String userId);
}
