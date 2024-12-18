package xeat.blogservice.recommend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xeat.blogservice.global.response.PageResponseDto;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RecommendListPageResponseDto {

    private PageResponseDto pageInfo;

    private Long targetBlogId;

    private List<RecommendListResponseDto> recommendList;

    public static RecommendListPageResponseDto toDto(PageResponseDto pageInfo, Long targetBlogId, List<RecommendListResponseDto> recommendList) {
        return new RecommendListPageResponseDto(
                pageInfo,
                targetBlogId,
                recommendList
        );
    }
}
