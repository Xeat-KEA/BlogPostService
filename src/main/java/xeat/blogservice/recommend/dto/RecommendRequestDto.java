package xeat.blogservice.recommend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "좋아요 요청 및 취소 관련 DTO")
public class RecommendRequestDto {

    @Schema(description = "좋아요 요청 및 취소 할 게시글 고유 ID", example = "1")
    private Long articleId;

    @Schema(description = "좋아요 요청 및 취소 한 사용자 고유 ID", example = "1")
    private Long userId;
}
