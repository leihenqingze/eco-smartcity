package com.eco.wisdompark;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.eco.wisdompark.mapper")
public class WisdomparkApplication {

    public static void main(String[] args) {
        SpringApplication.run(WisdomparkApplication.class, args);
    }
}
