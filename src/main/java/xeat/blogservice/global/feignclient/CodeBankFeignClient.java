package xeat.blogservice.global.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import xeat.blogservice.global.Response;

@FeignClient(name = "code-bank-service")
public interface CodeBankFeignClient {

    @GetMapping("/blog/user/{codeId}")
    CodeBankInfoResponseDto getCodeBankInfo(@RequestHeader("UserId") String userId, @PathVariable("codeId") Integer codeId);
}
