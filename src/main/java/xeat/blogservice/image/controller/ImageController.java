package xeat.blogservice.image.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import xeat.blogservice.image.dto.UploadImageResponse;
import xeat.blogservice.image.service.ImageService;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Tag(name = "블로그 서비스 이미지", description = "블로그 서비스 이미지 관련 api")
@RestController
@RequestMapping("/blog/article/image")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @Operation(summary = "사용자 이미지 업로드", description = "사용자가 이미지를 업로드 하였을 때 필요한 API")
    @Parameters({
            @Parameter(name = "image", description = "업로드할 이미지 파일", required = true)
    })
    @PostMapping("/upload")
    public UploadImageResponse uploadImage(@RequestParam("image") MultipartFile file) throws Exception {
        return imageService.uploadImage(file);
    }
}
