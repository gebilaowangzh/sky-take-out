package com.sky.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "sky.upload")
@Data
public class UploadProperties {
    //文件上传配置
    private String uploadDir;
    //默认后缀
    private String imageSuffix;
    //图片地址
    private String imgUrl;
    //上传文件名中是否使用原文件名
    private boolean useOriginalName;

}
