package xeat.blogservice.global;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import xeat.blogservice.announce.entity.Announce;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PageResponseDto {

    private Integer currentPageNum;

    private Integer totalPageNum;

    public static PageResponseDto toDto(Page<Announce> announcePageList) {
        return new PageResponseDto(
                announcePageList.getNumber() + 1,
                announcePageList.getTotalPages()
        );
    }
}
