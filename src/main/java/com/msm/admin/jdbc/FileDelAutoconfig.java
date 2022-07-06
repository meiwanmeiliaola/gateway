package com.msm.admin.jdbc;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 赵项阳
 *
 * @author DELL
 * @date 2022/5/6 15:57
 */
@Configuration
//使使用 @ConfigurationProperties 注解的类生效
@EnableConfigurationProperties({FileDelProperties.class})
public class FileDelAutoconfig {
}
