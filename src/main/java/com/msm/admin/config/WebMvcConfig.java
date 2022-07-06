package com.msm.admin.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.*;

import java.util.List;

/**
 * 资源路径映射
 * @author quavario@gmail.com
 * @date 2019/12/18 10:34
 */

@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {


    @Autowired
    private FileUploadProperties fileUploadProperties;

    /**
     * 文件上传虚拟路径映射
     *  通过这个配置可用访问上传的内容
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        // TODO 添加水印 临时使用java返回图片
        registry.addResourceHandler(fileUploadProperties.getUrl()+"/**").addResourceLocations("file:"+fileUploadProperties.getMappingPath()+"/"+fileUploadProperties.getApplicationName()+"/");
        registry.addResourceHandler("/file/**").addResourceLocations("file:G:/BaiduNetdiskDownload/data/data-mapping/msm_admin_static/static/file/");
        registry.addResourceHandler("/wwtm/**").addResourceLocations("file:G:/BaiduNetdiskDownload/data/data-mapping/wwtm/");
        registry.addResourceHandler("/lishiwenhua/**").addResourceLocations("file:G:/BaiduNetdiskDownload/data/data-mapping/lishiwenhua/");
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:static/");
//        registry.addResourceHandler("/file/**").addResourceLocations("file:G:/BaiduNetdiskDownload/data/data-mapping/msm_admin_static/static");
    }

    /**
     * 禁用跨域策略
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
                .maxAge(3600)
                .allowCredentials(true);
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(0, new MappingJackson2HttpMessageConverter());
    }

    /**
     * 增加图片转换器
     * @param converters
     */
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new BufferedImageHttpMessageConverter());
    }


}