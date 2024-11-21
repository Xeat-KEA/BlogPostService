package xeat.blogservice.global.minio;

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

import java.util.*;
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

        String content = Arrays.toString(Base64.getDecoder().decode(originalContent));

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

        System.out.println(urlAndContent.get(0));
        System.out.println(urlAndContent.get(1));

        return urlAndContent;
    }

    public List<String> editArticleImage(List<String> originalUrlAndContent) throws Exception{
        List<String> newUrlAndContent = new ArrayList<>();

        String thumbnailImageUrl = originalUrlAndContent.get(0);
        String content = Arrays.toString(Base64.getDecoder().decode(originalUrlAndContent.get(1)));

        Pattern pattern = Pattern.compile("http://172\\.16\\.211\\.113:9000/(postimage|uploadimage)/([\\w\\-]+(?:_[\\w\\-]+)*\\.[a-zA-Z]+)(?=\")");
        Matcher matcher = pattern.matcher(content);


        boolean createdThumbnail = false;

        while (matcher.find()) {

            String updateImagePath = matcher.group(0);
            String fileName = matcher.group(2);

            if (isImageExist(fileName)) {
                content = handleImage(updateImagePath, fileName, content);
            }

            String thumbnailImageName = changeToThumbnailName(thumbnailImageUrl);

            // 게시글 본문에 있는 첫 번째 이미지 + 첫 번째 이미지의 썸네일이 없을 경우
            if (!createdThumbnail) {
                if (!isThumbnailImageExist(thumbnailImageName)) {
                    thumbnailImageUrl = makeThumbnailImage(fileName);
                }
                else {
                    thumbnailImageUrl = thumbnailBucketUrl + thumbnailImageName;
                }
            }

            createdThumbnail = true;
        }
        newUrlAndContent.add(0, thumbnailImageUrl);
        newUrlAndContent.add(1, content);
        return newUrlAndContent;
    }

    public String editBlogImage(String originalContent) throws Exception {

        String content = Arrays.toString(Base64.getDecoder().decode(originalContent));

        Pattern pattern = Pattern.compile("http://172\\.16\\.211\\.113:9000/(postimage|uploadimage)/([\\w\\-]+(?:_[\\w\\-]+)*\\.[a-zA-Z]+)(?=\")");
        Matcher matcher = pattern.matcher(content);

        while (matcher.find()) {

            String updateImagePath = matcher.group(0);
            String fileName = matcher.group(2);

            if (isImageExist(fileName)) {
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
        return content;
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
