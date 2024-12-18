package xeat.blogservice.follow.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xeat.blogservice.global.feignclient.UserFeignClient;
import xeat.blogservice.global.feignclient.UserInfoResponseDto;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FollowerListResponseDto {

    private Long blogId;

    private String profileUrl;

    private String userName;

    public static FollowerListResponseDto toDto(Long followBlogId, UserInfoResponseDto userInfo) {
        return new FollowerListResponseDto(
                followBlogId,
                userInfo.getProfileUrl(),
                userInfo.getNickName()
        );
    }
}
