package xeat.blogservice.article.dto;

import jakarta.persistence.Access;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xeat.blogservice.global.userclient.UserFeignClient;
import xeat.blogservice.global.userclient.UserFeignClientResponseDto;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FeignClientTestDto {

    private String userId;

    private String nickName;

    public static FeignClientTestDto toDto(UserFeignClientResponseDto userFeignClientResponseDto) {
        return new FeignClientTestDto(
                userFeignClientResponseDto.getUserId(),
                userFeignClientResponseDto.getNickName()
        );
    }
}
