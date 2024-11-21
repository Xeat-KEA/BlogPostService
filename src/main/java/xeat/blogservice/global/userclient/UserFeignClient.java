package xeat.blogservice.global.userclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "user-service")
public interface UserFeignClient {

    @GetMapping(value = "/users/info")
    UserInfoResponseDto getUserInfo(@RequestHeader("UserId") String userId);
}
