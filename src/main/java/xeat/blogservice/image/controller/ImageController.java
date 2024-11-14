package xeat.blogservice.image.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import xeat.blogservice.image.service.ImageService;

@RestController
@RequestMapping("/blog/article/image")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @PostMapping("/upload")
    public String uploadImage(@RequestParam("image")MultipartFile file) throws Exception {
        return imageService.uploadImage(file);
    }
}
