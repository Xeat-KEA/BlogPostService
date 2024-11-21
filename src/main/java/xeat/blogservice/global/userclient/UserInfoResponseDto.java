package xeat.blogservice.global.userclient;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoResponseDto {

    private String userId;

    private String nickName;

    private String profileUrl;

    private String profileMessage;

    private String rank;
}
