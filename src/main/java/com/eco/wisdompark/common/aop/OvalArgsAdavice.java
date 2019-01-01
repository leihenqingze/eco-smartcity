package com.eco.wisdompark.common.aop;

import net.sf.oval.ConstraintViolation;
import net.sf.oval.Validator;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * @author litao
 * 参数校验AOP
 */
@Aspect
@Component
public class OvalArgsAdavice {

    private Validator validator = new Validator();

    @Pointcut("execution(public * com.eco.wisdompark.controller..*.*(..))")
    public void checkArgs() {
    }

    @Before("checkArgs()")
    public void doBefore(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        Arrays.stream(args).forEach(arg -> {
            List<ConstraintViolation> constraintViolations = validator.validate(arg);
            if (!constraintViolations.isEmpty()) {
                throw new IllegalArgumentException(constraintViolations.get(0).getMessage());
            }
        });
    }


}
