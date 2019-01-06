package com.eco.wisdompark;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.MultipartConfigElement;

/**
 * Created by haihao on  2019/1/5.
 */
@Configuration
public class MultipartConfig {

    /**
     * 文件上传配置
     * @return
     */
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        // 设置单个文件最大
        factory.setMaxFileSize("256KB"); //KB,MB
        // 设置总上传数据总大小
        factory.setMaxRequestSize("10240KB");
        return factory.createMultipartConfig();
    }

}
