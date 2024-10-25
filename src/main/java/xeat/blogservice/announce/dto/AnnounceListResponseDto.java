package xeat.blogservice.announce.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xeat.blogservice.announce.entity.Announce;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AnnounceListResponseDto {

    private Long announceId;

    private String title;

    private LocalDateTime createdDate;

    public static AnnounceListResponseDto toDto(Announce announce) {
        return new AnnounceListResponseDto(
                announce.getId(),
                announce.getTitle(),
                announce.getCreatedDate()
        );
    }
}
