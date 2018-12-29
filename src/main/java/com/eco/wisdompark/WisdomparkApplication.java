package com.eco.wisdompark;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@MapperScan("com.eco.wisdompark.mapper")
@Configuration
public class WisdomparkApplication {
    public static void main(String[] args) {
        SpringApplication.run(WisdomparkApplication.class, args);
    }
}