package xeat.blogservice.announce.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xeat.blogservice.global.PageResponseDto;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "공지사항 목록 조회 페이징 처리 응답 DTO")
public class AnnounceListPageResponseDto {

    @Schema(description = "페이지 정보", example = "{\"currentPageNum\": 1, \"totalPageNum\": 1}")
    private PageResponseDto pageInfo;

    @Schema(description = "공지사항 목록", example = "["
            + "{\"announceId\": 3, \"title\": \"제목3\", \"createdDate\": \"2024-10-25T17:32:00\"},"
            + "{\"announceId\": 2, \"title\": \"제목2\", \"createdDate\": \"2024-10-25T17:31:00\"},"
            + "{\"announceId\": 1, \"title\": \"제목1\", \"createdDate\": \"2024-10-25T17:30:00\"}"
            + "]")
    private List<AnnounceListResponseDto> announceList;

    public static AnnounceListPageResponseDto toDto(PageResponseDto pageInfo, List<AnnounceListResponseDto> announceList) {
        return new AnnounceListPageResponseDto(
                pageInfo,
                announceList
        );
    }
}