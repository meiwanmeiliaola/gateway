package com.msm.admin.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 文件上传属性类
 * @author quavario@gmail.com
 * @date 2019/3/6 14:15
 */
@ConfigurationProperties(prefix = "file")
public class FileUploadProperties {
    /**
     * 文件上传本地路径
     * 用于路径映射
     */
    //private String path;

    // 文件映射路径 win E:/Dev/msm_data_mapping
    @Getter
    @Setter
    private String mappingPath;

    /**
     * 配置上传文件url映射路径 例如配置url=/data, 上传文件1.jpg后可以通过localhost/data/1.jpg访问, 默认data
     */
    @Getter
    @Setter
    private String url;

    @Value("${spring.application.name}")
    @Getter
    @Setter
    private String applicationName;


    /**
     * 图片上传地址, 默认为 /data/museum/data-mapping/<application.name>/<fileType> 不建议修改
     */
    //private String imgLocalPath;

    /**
     * 文件上传地址, 默认为 /data/museum/data-mapping/<application.name>/<fileType> 不建议修改
     */
    //private String dataLocalPath;

    //压缩包路径
    //private String archiveLocalPath;

}
