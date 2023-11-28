package com.sky.controller.admin;

import java.io.IOException;
import java.util.UUID;

import com.sky.properties.UploadProperties;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sky.result.Result;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/admin/common")
@Slf4j
@Api(tags = "通用接口")
public class CommonController {
    //拿到配置类读取yml配置信息
    @Autowired
    public UploadProperties uploadProperties;


    /**
     * 上传图片
     *
     * @param file
     * @return
     */
    @PostMapping("/upload")
    @ApiOperation("文件上传")
    public Result<String> upload(MultipartFile file) {


        log.info("上传图片：{}", file);
        log.debug("Upload Directory: {}, Image Suffix: {}, Image URL: {}, Use Original Name for Upload: {}",
                uploadProperties.getUploadDir(),
                uploadProperties.getImageSuffix(),
                uploadProperties.getImgUrl(),
                uploadProperties.isUseOriginalName());
        String originalFilename = file.getOriginalFilename();
        //默认后缀
        String suffix = uploadProperties.getImageSuffix();
        if (originalFilename != null) {
            suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        String fileName;
        if (uploadProperties.isUseOriginalName()) {
            fileName = originalFilename + suffix;
        } else {
            fileName = UUID.randomUUID().toString() + suffix;
        }


        String imgUrl =  uploadProperties.getImgUrl() +fileName;
        try {
            file.transferTo(new java.io.File(uploadProperties.getUploadDir() + fileName));

            return Result.success(imgUrl);
        } catch (IllegalStateException | IOException e) {
            log.error("上传图片失败：{}", e);
            return Result.error("上传图片失败");
        }
    }
}
