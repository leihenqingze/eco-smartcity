package com.eco.wisdompark;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan("com.eco.wisdompark.mapper")
@Configuration
@EnableScheduling
@EnableCaching
@EnableTransactionManagement
public class WisdomparkApplication {
    public static void main(String[] args) {
        SpringApplication.run(WisdomparkApplication.class, args);
    }
}