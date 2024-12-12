package xeat.blogservice.notice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CodeNoticeSaveRequestDto {

    private String userId;

    private String title;

    private Boolean admit;
}
