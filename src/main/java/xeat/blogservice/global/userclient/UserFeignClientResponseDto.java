package xeat.blogservice.global.userclient;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserFeignClientResponseDto {

    private String userId;

    private String nickName;
}
