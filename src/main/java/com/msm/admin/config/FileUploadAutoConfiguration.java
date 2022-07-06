package com.msm.admin.config;

import com.msm.admin.modules.common.constant.FileType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.nio.file.FileAlreadyExistsException;

/**
 * 文件上传自动配置
 * @author quavario@gmail.com
 * @date 2019/4/18 14:11
 */
@Configuration
//使使用 @ConfigurationProperties 注解的类生效
@EnableConfigurationProperties({FileUploadProperties.class})
public class FileUploadAutoConfiguration {
    @Value("${spring.application.name}")
    private String applicationName;

    private static final String DEFAULT_URL_MAPPING = "/data";

    private static final String DEFAULT_WIN_UPLOAD_PATH = "E:/Dev/msm_data_mapping";
    private static final String DEFAULT_LINUX_UPLOAD_PATH = "/data/museum/data-mapping";

    @Bean
    public FileUploadProperties fileUploadProperties() throws FileAlreadyExistsException {
        FileUploadProperties properties = new FileUploadProperties();
        String url = DEFAULT_URL_MAPPING;
        String mappingPath;
        String os = System.getProperty("os.name");
        if (os.toLowerCase().contains("win")) {
            mappingPath = DEFAULT_WIN_UPLOAD_PATH;
        } else {
            mappingPath = DEFAULT_LINUX_UPLOAD_PATH;
        }

//        String[] folders = new String[]{"image", "data", "video"};
        //上传路径
        String path = mappingPath + "/" + applicationName+ "/";
        for (String folder : FileType.list) {
            File file = new File(path + folder);
            if (!file.exists()) {
                file.mkdirs();
            }
        }
        properties.setApplicationName(applicationName);
        properties.setMappingPath(mappingPath);
        properties.setUrl(url);
        return properties;
    }
}
