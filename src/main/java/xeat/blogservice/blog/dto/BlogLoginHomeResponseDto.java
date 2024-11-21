package xeat.blogservice.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xeat.blogservice.blog.entity.Blog;
import xeat.blogservice.global.userclient.UserInfoResponseDto;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BlogLoginHomeResponseDto {

    private Long blogId;

    private String rank;

    private String userName;

    private String profileMessage;

    private Integer followCount;

    private Boolean followCheck;

    private String profileUrl;


    public static BlogLoginHomeResponseDto toDto(Blog blog, UserInfoResponseDto userInfoResponseDto, String rank, Boolean followCheck) {
        return new BlogLoginHomeResponseDto(
                blog.getId(),
                rank,
                userInfoResponseDto.getNickName(),
                userInfoResponseDto.getProfileMessage(),
                blog.getFollowCount(),
                followCheck,
                userInfoResponseDto.getProfileUrl()
        );
    }
}
