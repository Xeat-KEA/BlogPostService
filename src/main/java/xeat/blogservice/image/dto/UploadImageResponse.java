package xeat.blogservice.image.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UploadImageResponse {

    private String uploadImageUrl;

    public static UploadImageResponse toDto(String uploadImageUrl) {
        return new UploadImageResponse(uploadImageUrl);
    }
}
