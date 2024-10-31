package xeat.blogservice.follow.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "팔로우 요청 및 취소에 필요한 DTO")
public class FollowRequestDto {

    @Schema(description = "팔로우 요청을 받은 사용자 고유 ID", example = "1(팔로우 요청을 받은 사용자 고유 ID)")
    private Long userId;

    @Schema(description = "팔로우 요청을 한 사용자 고유 ID", example = "2(팔로우 요청을 한 사용자 고유 ID)")
    private Long followUserId;
}
