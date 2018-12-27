package com.eco.wisdompark.common.aop;

import net.sf.oval.ConstraintViolation;
import net.sf.oval.Validator;
import net.sf.oval.exception.ConstraintsViolatedException;
import net.sf.oval.guard.Guard;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class OvalArgsAdavice {

//    @Pointcut
//    @Pointcut("@annotation(com.test.oval.OvalArgsCheck)")
//    public void checkArgs() {
//    }
//
//    @Before("checkArgs()")
//    public void joint(JoinPoint joinPoint) throws Exception {
//
//        MethodInvocationProceedingJoinPoint mjp =
//                ((MethodInvocationProceedingJoinPoint) joinPoint);
//
//        // *) 获取methodInvocation对象
//        MethodInvocation mi = null;
//        try {
//            Field field = MethodInvocationProceedingJoinPoint
//                    .class.getDeclaredField("methodInvocation");
//            field.setAccessible(true);
//            mi = (MethodInvocation)field.get(mjp);
//        } catch (Throwable e) {
//        }
//
//        if ( mi != null ) {
//            // 获取Guard对象的validateMethodParameters方法
//            Guard guard = new Guard();
//            Method dm = Guard.class.getDeclaredMethod(
//                    "validateMethodParameters",
//                    Object.class,
//                    Method.class,
//                    Object[].class,
//                    List.class
//            );
//            dm.setAccessible(true);
//
//            // *) 对函数中标注Oval注解的参数, 直接进行校验, 用于解决第一类问题
//            List<ConstraintViolation> violations = new ArrayList<ConstraintViolation>();
//            dm.invoke(guard, mi.getThis(), mi.getMethod(), mi.getArguments(), violations);
//            if ( violations.size() > 0 ) {
//                throw new ConstraintsViolatedException(violations);
//            }
//
//            // *) 以下是对函数中实体类(内部属性标记Oval注解)的参数, 进行校验, 用于解决第二类问题
//            Validator validator = new Validator();
//            for ( Object obj : mi.getArguments() ) {
//                if ( obj == null ) continue;
//                List<ConstraintViolation> cvs = validator.validate(obj);
//                if ( cvs != null && cvs.size() > 0 ) {
//                    throw new ConstraintsViolatedException(cvs);
//                }
//            }
//
//        }
//
//    }

}
