package xeat.blogservice.article.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xeat.blogservice.global.userclient.UserInfoResponseDto;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FeignClientTestDto {

    private String userId;

    private String nickName;

    public static FeignClientTestDto toDto(UserInfoResponseDto userInfoResponseDto) {
        return new FeignClientTestDto(
                userInfoResponseDto.getUserId(),
                userInfoResponseDto.getNickName()
        );
    }
}
