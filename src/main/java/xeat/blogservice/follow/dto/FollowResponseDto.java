package xeat.blogservice.follow.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FollowResponseDto {

    private Integer followCount;

    public static FollowResponseDto toDto(Integer followCount) {
        return new FollowResponseDto(followCount);
    }
}
