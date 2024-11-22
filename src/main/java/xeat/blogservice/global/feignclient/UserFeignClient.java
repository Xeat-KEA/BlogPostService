package xeat.blogservice.global.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "user-service", url = "http://172.16.211.57:44501")
public interface UserFeignClient {

    @GetMapping(value = "/user-service/users/info")
    UserInfoResponseDto getUserInfo(@RequestHeader("UserId") String userId);
}
