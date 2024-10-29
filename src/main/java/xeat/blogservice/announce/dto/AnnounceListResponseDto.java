package xeat.blogservice.announce.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xeat.blogservice.announce.entity.Announce;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "공지사항 목록 조회 응답")
public class AnnounceListResponseDto {

    @Schema(description = "공지사항 고유 Id", example = "1")
    private Long announceId;

    @Schema(description = "공지사항 제목", example = "제목1")
    private String title;

    @Schema(description = "공지사항 생성 일자", example = "2024-10-27T22:17:38.4213912")
    private LocalDateTime createdDate;

    public static AnnounceListResponseDto toDto(Announce announce) {
        return new AnnounceListResponseDto(
                announce.getId(),
                announce.getTitle(),
                announce.getCreatedDate()
        );
    }
}
