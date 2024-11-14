package xeat.blogservice.image.service;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final MinioClient minioClient;

    @Value("${minio.url}")
    private String minioBaseUrl;

    @Value("${minio.articleUpload.bucket}")
    private String minioUploadBucket;
    @Value("${minio.uploadBucket.url}")
    private String uploadBucketUrl;
    @Value("${minio.articleSave.bucket}")
    private String minioSaveBucket;
    @Value("${minio.saveBucket.url}")
    private String saveBucketUrl;

    public String uploadImage(MultipartFile file) throws Exception {
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

        InputStream inputStream = file.getInputStream();
        minioClient.putObject(PutObjectArgs.builder()
                .bucket(minioUploadBucket)
                .object(fileName)
                .stream(inputStream, file.getSize(), -1)
                .contentType(file.getContentType())
                .build()
        );
        return uploadBucketUrl + fileName;
    }
}
