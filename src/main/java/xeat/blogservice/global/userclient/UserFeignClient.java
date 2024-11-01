package xeat.blogservice.global.userclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "user-service")
public interface UserFeignClient {

    @GetMapping("/users/userInfo")
    UserFeignClientResponseDto getUserInfo(@RequestHeader("UserId") String userId);
}
