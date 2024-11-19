package xeat.blogservice.global;

import io.minio.*;
import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class MinioImageService {

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

    public List<String> saveImage(String originalContent) throws Exception {

        List<String> urlAndContent = new ArrayList<>();

        String content = URLDecoder.decode(originalContent, StandardCharsets.UTF_8);

        String thumbnailImageUrl = null;

        // 초기 값 설정
        urlAndContent.add(0, thumbnailImageUrl);
        urlAndContent.add(1, content);

        Pattern pattern = Pattern.compile("http://172\\.16\\.211\\.113:9000/articleupload/([\\w\\-]+(?:_[\\w\\-]+)*\\.[a-zA-Z]+)(?=\")");
        Matcher matcher = pattern.matcher(content);

        boolean createdThumbnail = false;

        while (matcher.find()) {
            String updateImagePath = matcher.group(0);
            String fileName = matcher.group(1);
            urlAndContent = handleImage(updateImagePath, fileName, thumbnailImageUrl, createdThumbnail, content);
            thumbnailImageUrl = urlAndContent.get(0);
            content = urlAndContent.get(1);
            createdThumbnail = true;
        }

        return urlAndContent;
    }

    public List<String> editImage(List<String> originalUrlAndContent) throws Exception{
        List<String> newUrlAndContent = new ArrayList<>();

        String content = URLDecoder.decode(originalUrlAndContent.get(1), StandardCharsets.UTF_8);

        Pattern pattern = Pattern.compile("http://172\\.16\\.211\\.113:9000/(articlesave|articleupload)/([\\w\\-]+(?:_[\\w\\-]+)*\\.[a-zA-Z]+)(?=\")");
        Matcher matcher = pattern.matcher(content);

        String thumbnailImageUrl = originalUrlAndContent.get(0);

        boolean createdThumbnail = false;

        while (matcher.find()) {

            if (isImageExist(matcher.group(2))) {
                String updateImagePath = matcher.group(0);
                String fileName = matcher.group(2);
                newUrlAndContent = handleImage(updateImagePath, fileName, thumbnailImageUrl, createdThumbnail, content);
                thumbnailImageUrl = newUrlAndContent.get(0);
                content = newUrlAndContent.get(1);
            }
            createdThumbnail = true;
        }
        return newUrlAndContent;
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

        // 기존 이미지 확장자 추출
        String extension = fileName.substring(fileName.lastIndexOf('.'));

        // 썸네일 이미지 이름 생성
        String thumbnailFileName = UUID.randomUUID() + fileName + "_thumbnail" + extension;

        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(thumbnailBucket)
                        .object(thumbnailFileName)
                        .stream(thumbnailInputStream, byteArrayOutputStream.size(), -1)
                        .contentType(originalContentType)
                        .build());

        return thumbnailBucketUrl + thumbnailFileName;
    }

    public List<String> handleImage(String updateImagePath, String fileName, String originalThumbnailImageUrl,
                                    Boolean createdThumbnail, String content) throws Exception{


        List<String> urlAndContent = new ArrayList<>();

        String thumbnailImageUrl = originalThumbnailImageUrl;

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

        if (!createdThumbnail) {
            if (!isThumbnailImageExist(fileName)) {
                thumbnailImageUrl = makeThumbnailImage(fileName);
            }
        }

        urlAndContent.add(0, thumbnailImageUrl);
        urlAndContent.add(1, content);
        return urlAndContent;
    }

    public boolean isImageExist(String fileName) {
        try {
            // 객체 존재 여부 확인
            minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(minioUploadBucket)
                            .object(fileName)
                            .build());
            System.out.println("true");
            return true;  // 객체가 존재하면 true를 반환

        } catch (Exception e) {
            System.out.println("false");
            return false;  // 예외가 발생하면 객체가 존재하지 않는 것으로 처리
        }
    }

    public boolean isThumbnailImageExist(String fileName) {
        try {
            String baseName = fileName.substring(0, fileName.lastIndexOf("."));
            String extension = fileName.substring(fileName.lastIndexOf("."));

            String thumbnailName = baseName + "_thumbnail" + extension;

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
}
