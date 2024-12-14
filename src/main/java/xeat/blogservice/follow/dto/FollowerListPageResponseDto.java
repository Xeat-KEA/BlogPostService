package xeat.blogservice.follow.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xeat.blogservice.codearticle.dto.CodeArticleListPageResponseDto;
import xeat.blogservice.codearticle.dto.CodeArticleListResponseDto;
import xeat.blogservice.global.response.PageResponseDto;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FollowerListPageResponseDto {

    private PageResponseDto pageInfo;

    private Long targetBlogId;

    private List<FollowerListResponseDto> followerList;

    public static FollowerListPageResponseDto toDto(PageResponseDto pageInfo, Long targetBlogId, List<FollowerListResponseDto> followerList) {
        return new FollowerListPageResponseDto(
                pageInfo,
                targetBlogId,
                followerList
        );
    }
}
