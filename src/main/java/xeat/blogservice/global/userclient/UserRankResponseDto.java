package xeat.blogservice.global.userclient;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserRankResponseDto {

    private Long userId;

    private Integer totalScore;

    public String getRank(Integer totalScore) {
        if (totalScore <= 100) {
            return "freshman";
        }
        else if (totalScore > 100 && totalScore <= 1000) {
            return "sophomore";
        }
        else if (totalScore > 1000 && totalScore <= 10000) {
            return "junior";
        }
        else {
            return "senior";
        }
    }
}
