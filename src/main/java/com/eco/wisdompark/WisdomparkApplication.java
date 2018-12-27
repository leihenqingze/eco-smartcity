package com.eco.wisdompark;

import net.sf.oval.Validator;
import net.sf.oval.configuration.xml.XMLConfigurer;
import net.sf.oval.guard.GuardInterceptor;
import net.sf.oval.integration.spring.SpringInjector;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

@SpringBootApplication
@MapperScan("com.eco.wisdompark.mapper")
@Configuration
public class WisdomparkApplication {

    @Bean
    public GuardInterceptor ovalGuardInterceptor() {
        return new GuardInterceptor();
    }

    @Bean
    public BeanNameAutoProxyCreator beanNameAutoProxyCreator() {
        BeanNameAutoProxyCreator beanNameAutoProxyCreator = new BeanNameAutoProxyCreator();
        beanNameAutoProxyCreator.setProxyTargetClass(true);
        beanNameAutoProxyCreator.setBeanNames("*Service");
        beanNameAutoProxyCreator.setInterceptorNames("ovalGuardInterceptor");
        return beanNameAutoProxyCreator;
    }

    @Bean
    public SpringInjector springInjector() throws IllegalAccessException, InstantiationException,
            NoSuchMethodException, InvocationTargetException {
        Constructor<SpringInjector> constructor = SpringInjector.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        return constructor.newInstance();
    }

    @Bean
    public Validator validator() {
        Validator validator = new Validator();
        return validator;
    }

    public static void main(String[] args) {
        SpringApplication.run(WisdomparkApplication.class, args);
    }
}