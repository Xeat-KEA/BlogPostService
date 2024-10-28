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
@Schema(description = "공지사항 상세 조회 응답 DTO")
public class GetAnnounceResponseDto {

    @Schema(description = "공지사항 고유 Id", example = "1")
    private Long announceId;

    @Schema(description = "공지사항 제목", example = "제목1")
    private String title;

    @Schema(description = "공지사항 내용", example = "내용1")
    private String content;

    @Schema(description = "공지사항 생성 일자", example = "2024-10-27T22:17:38.4213912")
    private LocalDateTime createdDate;

    public static GetAnnounceResponseDto toDto(Announce announce) {
        return new GetAnnounceResponseDto(
                announce.getId(),
                announce.getTitle(),
                announce.getContent(),
                announce.getCreatedDate()
        );
    }
}
