package com.sky.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "sky.upload")
@Data
public class UploadProperties {

    private String path;
    private String imageSuffix;
    private String accessKeySecret;
    private String bucketName;

}
