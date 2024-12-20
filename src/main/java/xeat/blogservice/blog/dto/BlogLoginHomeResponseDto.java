package xeat.blogservice.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xeat.blogservice.blog.entity.Blog;
import xeat.blogservice.global.feignclient.UserInfoResponseDto;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BlogLoginHomeResponseDto {

    private Long blogId;

    private String tier;

    private String userName;

    private String profileMessage;

    private String profileUrl;

    private Integer followCount;

    private Boolean followCheck;

    private String mainContent;


    public static BlogLoginHomeResponseDto toDto(Blog blog, String mainContent, UserInfoResponseDto userInfoResponseDto, Boolean followCheck) {
        return new BlogLoginHomeResponseDto(
                blog.getId(),
                userInfoResponseDto.getTier(),
                userInfoResponseDto.getNickName(),
                userInfoResponseDto.getProfileMessage(),
                userInfoResponseDto.getProfileUrl(),
                blog.getFollowCount(),
                followCheck,
                mainContent
        );
    }
}
