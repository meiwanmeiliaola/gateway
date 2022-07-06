package com.msm.admin.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author quavario@gmail.com
 * @date 2020/5/20 17:24
 */
@ConfigurationProperties("encrypt.key-store")
@Data
@Component
public class KeyStoreConfig {
    private String location;
    private String secret;
    private String alias;
    private String password;
    private String type;

}
