package xeat.blogservice.image.service;

import io.minio.*;
import io.minio.errors.MinioException;
import io.minio.messages.Item;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import xeat.blogservice.image.dto.UploadImageResponse;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageService {

    private final MinioClient minioClient;

    @Value("${minio.upload.bucket}")
    private String minioUploadBucket;
    @Value("${minio.uploadBucket.url}")
    private String uploadBucketUrl;
    @Value("${minio.post.bucket}")
    private String minioPostBucket;
    @Value("${minio.postBucket.url}")
    private String postBucketUrl;
    @Value("${minio.thumbnail.bucket}")
    private String thumbnailBucket;
    @Value("${minio.thumbnailBucket.url}")
    private String thumbnailBucketUrl;

    @Scheduled(cron = "0 0 0 * * ?")
    public void deleteOldFiles() throws Exception {

        // Minio에서 uploadBucket에 있는 이미지 목록 불러오기
        Iterable<Result<Item>> items = minioClient.listObjects(
                ListObjectsArgs.builder()
                        .bucket(minioUploadBucket)
                        .build());


        for (Result<Item> result : items) {
            Item item = result.get();

            Instant lastModified = item.lastModified().toInstant();

            // 파일이 1일 이상된 경우 삭제
            if (Duration.between(lastModified, Instant.now()).toDays() >= 1) {
                minioClient.removeObject(
                        RemoveObjectArgs.builder()
                                .bucket(minioUploadBucket)
                                .object(item.objectName())
                                .build()
                );
                log.info("Deleted Image = {}, Uploaded Date = {}", item.objectName(), item.lastModified());
            }
        }
    }

    public UploadImageResponse uploadImage(MultipartFile file) throws Exception {
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

        InputStream inputStream = file.getInputStream();
        minioClient.putObject(PutObjectArgs.builder()
                .bucket(minioUploadBucket)
                .object(fileName)
                .stream(inputStream, file.getSize(), -1)
                .contentType(file.getContentType())
                .build()
        );
        return UploadImageResponse.toDto(uploadBucketUrl + fileName);
    }

    public String returnImageToUpload(String originalContent) throws Exception {

        Pattern pattern = Pattern.compile("http://172\\.16\\.211\\.113:9000/postimage/([\\w\\-]+(?:_[\\w\\-]+)*\\.[a-zA-Z]+)(?=\")");
        Matcher matcher = pattern.matcher(originalContent);

        String updateContent = originalContent;

        boolean isFirstImage = true;

        while (matcher.find()) {
            String originalImagePath = matcher.group(0);
            String fileName = matcher.group(1);

            // 썸네일 이미지가 존재할 시 삭제
            if (isFirstImage) {
                String thumbnailImageName = changeToThumbnailName(fileName);
                if (isThumbnailImageExist(thumbnailImageName)) {
                    minioClient.removeObject(
                            RemoveObjectArgs.builder()
                                    .bucket(thumbnailBucket)
                                    .object(thumbnailImageName)
                                    .build()
                    );
                }
                isFirstImage = false;
            }

            updateContent = returnImage(originalImagePath, fileName, updateContent);
        }
        return updateContent;
    }

    public List<String> saveImage(String originalContent) throws Exception {

        List<String> urlAndContent = new ArrayList<>();

        String content = new String(Base64.getDecoder().decode(originalContent));

        String thumbnailImageUrl = null;

        // 초기 값 설정
        urlAndContent.add(0, thumbnailImageUrl);
        urlAndContent.add(1, content);

        Pattern pattern = Pattern.compile("http://172\\.16\\.211\\.113:9000/uploadimage/([\\w\\-]+(?:_[\\w\\-]+)*\\.[a-zA-Z]+)(?=\")");
        Matcher matcher = pattern.matcher(content);

        boolean createdThumbnail = false;

        while (matcher.find()) {
            String updateImagePath = matcher.group(0);
            String fileName = matcher.group(1);

            content = handleImage(updateImagePath, fileName, content);


            if (!createdThumbnail) {
                thumbnailImageUrl = makeThumbnailImage(fileName);
            }

            createdThumbnail = true;
        }

        urlAndContent.add(0, thumbnailImageUrl);
        urlAndContent.add(1, content);

        return urlAndContent;
    }

    public String editBlogImage(String originalContent) throws Exception {

        String content = new String(Base64.getDecoder().decode(originalContent));

        Pattern pattern = Pattern.compile("http://172\\.16\\.211\\.113:9000/uploadimage/([\\w\\-]+(?:_[\\w\\-]+)*\\.[a-zA-Z]+)(?=\")");
        Matcher matcher = pattern.matcher(content);

        while (matcher.find()) {

            String updateImagePath = matcher.group(0);
            String fileName = matcher.group(1);

            if (isUploadImageExist(fileName)) {
                content = handleImage(updateImagePath, fileName, content);
            }
        }
        return content;
    }

    public String makeThumbnailImage(String fileName) throws Exception{

        // 썸네일 이미지 처리를 진행할 Minio 원본 이미지 Content-Type 추출
        String originalContentType = minioClient.statObject(
                        StatObjectArgs.builder()
                                .bucket(minioPostBucket)
                                .object(fileName)
                                .build())
                .contentType();


        InputStream originalImageInputStream = minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(minioPostBucket)
                        .object(fileName)
                        .build());

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Thumbnails.of(originalImageInputStream)
                .size(200, 200)
                .toOutputStream(byteArrayOutputStream);

        InputStream thumbnailInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());

        String thumbnailFileName = changeToThumbnailName(fileName);

        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(thumbnailBucket)
                        .object(thumbnailFileName)
                        .stream(thumbnailInputStream, byteArrayOutputStream.size(), -1)
                        .contentType(originalContentType)
                        .build());

        return thumbnailBucketUrl + thumbnailFileName;
    }

    public String handleImage(String updateImagePath, String fileName, String content) throws Exception{

        minioClient.copyObject(
                CopyObjectArgs.builder()
                        .bucket(minioPostBucket)
                        .object(fileName)
                        .source(
                                CopySource.builder()
                                        .bucket(minioUploadBucket)
                                        .object(fileName)
                                        .build())
                        .build());

        minioClient.removeObject(
                RemoveObjectArgs.builder()
                        .bucket(minioUploadBucket)
                        .object(fileName)
                        .build()
        );
        content = content.replace(updateImagePath, postBucketUrl + fileName);
        log.info("본문 내용={}", content);
        return content;
    }

    public String returnImage(String updateImagePath, String fileName, String content) throws Exception{

        minioClient.copyObject(
                CopyObjectArgs.builder()
                        .bucket(minioUploadBucket)
                        .object(fileName)
                        .source(
                                CopySource.builder()
                                        .bucket(minioPostBucket)
                                        .object(fileName)
                                        .build())
                        .build());

        minioClient.removeObject(
                RemoveObjectArgs.builder()
                        .bucket(minioPostBucket)
                        .object(fileName)
                        .build()
        );
        content = content.replace(updateImagePath, uploadBucketUrl + fileName);
        return content;
    }

    public boolean isUploadImageExist(String fileName) {
        try {
            // 이미지 존재 여부 확인
            minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(minioUploadBucket)
                            .object(fileName)
                            .build());
            return true;  // 이미지가 존재하면 true를 반환

        } catch (Exception e) {
            return false;  // 예외가 발생하면 이미지가 존재하지 않는 것으로 처리
        }
    }

    public boolean isThumbnailImageExist(String thumbnailName) {
        try {
            // 객체 존재 여부 확인
            minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(thumbnailBucket)
                            .object(thumbnailName)
                            .build());
            System.out.println("true");
            return true;  // 객체가 존재하면 true를 반환

        } catch (Exception e) {
            System.out.println("false");
            return false;  // 예외가 발생하면 객체가 존재하지 않는 것으로 처리
        }
    }

    public String changeToThumbnailName(String fileName) {

        String baseName = fileName.substring(0, fileName.lastIndexOf("."));
        // 기존 이미지 확장자 추출
        String extension = fileName.substring(fileName.lastIndexOf('.'));
        return baseName + "_thumbnail" + extension;
    }
}
