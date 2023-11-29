package com.alex.violet_course_navigator.service;

import com.alex.violet_course_navigator.exception.AwsS3UploadException;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

@Service
public class ImageStorageService {

  @Value("${s3.bucket}")
  private String bucketName;

  private final S3Client s3;

  public ImageStorageService(S3Client s3) {
    this.s3 = s3;
  }

  public String save(MultipartFile file) throws AwsS3UploadException {
    String filename = UUID.randomUUID().toString();
    PutObjectRequest objectRequest =
        PutObjectRequest.builder()
            .bucket(bucketName)
            .key(filename)
            .acl("public-read")
            .contentType("image/jpeg")
            .build();

    try (InputStream is = file.getInputStream()) {
      PutObjectResponse response =
          s3.putObject(objectRequest, RequestBody.fromInputStream(is, file.getSize()));

      if (response.sdkHttpResponse().statusCode() != 200) {
        throw new IOException(
            "Failed to upload file to S3, status code: " + response.sdkHttpResponse().statusCode());
      }

    } catch (Exception exception) {
      // print the full stack trace
      exception.printStackTrace();
    }
    return "https://" + bucketName + ".s3.amazonaws.com/" + filename;
  }
}
