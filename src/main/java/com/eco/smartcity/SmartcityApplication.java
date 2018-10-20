package com.eco.smartcity;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.eco.smartcity.mapper")
public class SmartcityApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartcityApplication.class, args);
    }
}
