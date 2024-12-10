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
import xeat.blogservice.blog.entity.Blog;
import xeat.blogservice.image.dto.UploadImageResponse;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
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

    public List<String> getOriginalImageList(String originalContent) throws Exception {

        List<String> originalImageList = new ArrayList<>();

        Pattern pattern = Pattern.compile("http://172\\.16\\.211\\.113:9000/postimage/([\\w\\-]+(?:_[\\w\\-]+)*\\.[a-zA-Z]+)(?=\")");
        Matcher matcher = pattern.matcher(originalContent);

        while (matcher.find()) {
            String fileName = matcher.group(1);
            originalImageList.add(fileName);
        }
        return originalImageList;
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

    public List<String> editArticleImage(String originalContent, String originalThumbnailImage, List<String> originalImageList) throws Exception {

        String content = new String(Base64.getDecoder().decode(originalContent));

        List<String> urlAndContent = new ArrayList<>();
        List<String> newImageList = new ArrayList<>();

        Pattern pattern = Pattern.compile("http://172\\.16\\.211\\.113:9000/(postimage|uploadimage)/([\\w\\-]+(?:_[\\w\\-]+)*\\.[a-zA-Z]+)(?=\")");
        Matcher matcher = pattern.matcher(content);

        String updateThumbnailImageUrl = originalThumbnailImage;

        boolean firstImageCaptured = false;

        while (matcher.find()) {

            String updateImagePath = matcher.group(0);
            String fileName = matcher.group(2);


            if (Objects.equals(matcher.group(1), "uploadimage")) {
                content = handleImage(updateImagePath, fileName, content);
            }
            else {
                newImageList.add(fileName);
            }

            if (!firstImageCaptured && !Objects.equals(originalThumbnailImage, thumbnailBucketUrl + fileName)) {
                String thumbnailImage = originalThumbnailImage.substring(originalThumbnailImage.lastIndexOf("/") + 1);
                minioClient.removeObject(
                        RemoveObjectArgs.builder()
                                .bucket(thumbnailBucket)
                                .object(thumbnailImage)
                                .build()
                );
                updateThumbnailImageUrl = makeThumbnailImage(fileName);
                firstImageCaptured = true;
            }

        }

        for (String image : originalImageList) {
            if (!newImageList.contains(image)) {
                minioClient.removeObject(
                        RemoveObjectArgs.builder()
                                .bucket(minioPostBucket)
                                .object(image)
                                .build()
                );
            }
        }

        urlAndContent.add(0, updateThumbnailImageUrl);
        urlAndContent.add(1, content);

        return urlAndContent;

    }

    public String editBlogImage(String originalContent, List<String> originalImageList) throws Exception {

        String content = new String(Base64.getDecoder().decode(originalContent));

        List<String> newImageList = new ArrayList<>();

        Pattern pattern = Pattern.compile("http://172\\.16\\.211\\.113:9000/(postimage|uploadimage)/([\\w\\-]+(?:_[\\w\\-]+)*\\.[a-zA-Z]+)(?=\")");
        Matcher matcher = pattern.matcher(content);

        while (matcher.find()) {

            String updateImagePath = matcher.group(0);
            String fileName = matcher.group(2);
            log.info("updateImagePath={}", matcher.group(0));
            log.info("fileName={}", matcher.group(2));
            log.info("content={}", matcher.group(1));

            if (Objects.equals(matcher.group(1), "uploadimage")) {
                content = handleImage(updateImagePath, fileName, content);
            }
            else {
                newImageList.add(fileName);
            }
        }

        if (originalImageList != null) {
            for (String image : originalImageList) {
                if (!newImageList.contains(image)) {
                    minioClient.removeObject(
                            RemoveObjectArgs.builder()
                                    .bucket(minioPostBucket)
                                    .object(image)
                                    .build()
                    );
                }
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

    public String changeToThumbnailName(String fileName) {

        String baseName = fileName.substring(0, fileName.lastIndexOf("."));
        // 기존 이미지 확장자 추출
        String extension = fileName.substring(fileName.lastIndexOf('.'));
        return baseName + "_thumbnail" + extension;
    }
}
