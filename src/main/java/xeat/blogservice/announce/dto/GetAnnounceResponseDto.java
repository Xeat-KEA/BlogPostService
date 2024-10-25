package xeat.blogservice.announce.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xeat.blogservice.announce.entity.Announce;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetAnnounceResponseDto {

    private Long announceId;

    private String title;

    private String content;

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
