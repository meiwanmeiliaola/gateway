package com.msm.admin.jdbc;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 赵项阳
 *
 * @author DELL
 * @date 2022/5/6 15:58
 */
@ConfigurationProperties(prefix = "filedel")
public class FileDelProperties {

    @Getter
    @Setter
    private String WinDelPath;

    @Getter
    @Setter
    private String LinDelPath;

    @Value("${spring.datasource.url}")
    @Getter
    @Setter
    private String url;

    @Value("${spring.datasource.username}")
    @Getter
    @Setter
    private String username;

    @Value("${spring.datasource.password}")
    @Getter
    @Setter
    private String password;


    @Value("${spring.datasource.driver-class-name}")
    @Getter
    @Setter
    private String driverClassName;

    public String getDefaultDelPath(){
        String os = System.getProperty("os.name");
        if (os.toLowerCase().contains("win")) {
            return WinDelPath;
        } else {
            return LinDelPath;
        }
    }
}
