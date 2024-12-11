package xeat.blogservice.recommend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xeat.blogservice.global.feignclient.UserInfoResponseDto;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RecommendListResponseDto {

    private Long blogId;

    private String profileUrl;

    private String userName;

    public static RecommendListResponseDto toDto(Long blogId, UserInfoResponseDto userInfo) {
        return new RecommendListResponseDto(
                blogId,
                userInfo.getProfileUrl(),
                userInfo.getNickName()
        );
    }
}
