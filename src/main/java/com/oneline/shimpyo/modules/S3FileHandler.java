package com.oneline.shimpyo.modules;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.oneline.shimpyo.domain.BaseException;
import com.oneline.shimpyo.domain.house.dto.FileReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class S3FileHandler {
    private final AmazonS3Client amazonS3Client;
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    @Value("${cloud.aws.region.static}")
    private String region;
    
    public Optional<FileReq> uploadFile(MultipartFile file) {
        try {
            String originalFileName = file.getOriginalFilename();
            String today = new SimpleDateFormat("yyMMdd").format(new Date()) + "/"; // 230615
            String extension = originalFileName.substring(originalFileName.lastIndexOf('.')); // .jpg
            String savedFileName = UUID.randomUUID() + extension;
            StringBuilder sb = new StringBuilder();
            sb.append("https://");
            sb.append(bucket);
            sb.append(".s3.");
            sb.append(region);
            sb.append(".amazonaws.com/");
            sb.append(today);
            sb.append(savedFileName);
            String savedURL = sb.toString();
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(file.getContentType());
            metadata.setContentLength(file.getSize());
            amazonS3Client.putObject(bucket, today + savedFileName, file.getInputStream(), metadata);
            FileReq fileinfo = FileReq.builder()
                    .originalFileName(originalFileName)
                    .savedURL(savedURL)
                    .build();
            return Optional.ofNullable(fileinfo);

        } catch (Exception e) {
            e.printStackTrace();
            return Optional.ofNullable(null);
        }
    }

    public void getFile(String fileURL) {
        String key = fileURL.substring(60);
    }
    public void removeFile(String fileURL) {
        try {
            String key = fileURL.substring(61);
            amazonS3Client.deleteObject(bucket, key);
        } catch (Exception e) {
            log.info("AWS S3 파일 제거 실패");
        }
    }

}
