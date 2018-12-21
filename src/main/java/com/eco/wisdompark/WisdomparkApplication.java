package com.eco.wisdompark;

import net.sf.oval.guard.GuardInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@MapperScan("com.eco.wisdompark.mapper")
public class WisdomparkApplication {

    @Bean
    public GuardInterceptor ovalGuardInterceptor() {
        return new GuardInterceptor();
    }

    @Bean
    public BeanNameAutoProxyCreator beanNameAutoProxyCreator() {
        BeanNameAutoProxyCreator beanNameAutoProxyCreator = new BeanNameAutoProxyCreator();
        beanNameAutoProxyCreator.setProxyTargetClass(true);
        beanNameAutoProxyCreator.setBeanNames("*Controller");
        beanNameAutoProxyCreator.setInterceptorNames("ovalGuardInterceptor");
        return beanNameAutoProxyCreator;
    }

    public static void main(String[] args) {
        SpringApplication.run(WisdomparkApplication.class, args);
    }
}