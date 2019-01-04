package com.eco.wisdompark.common.aop;

import com.eco.wisdompark.common.dto.ResponseData;
import com.eco.wisdompark.common.exceptions.WisdomParkException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

 /**
  * 登录拦截
  *
  * @author zhangkai
  * @date 2019/1/1 下午11:02
  */
@Aspect
@Component
public class OvalLoginAdavice {

    @Pointcut("execution(public * com.eco.wisdompark.controller..*.*(..))")
    public void checkLogin() {
    }

    @Before("checkLogin()")
    public void doBefore(JoinPoint joinPoint) {
//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//        HttpSession session = request.getSession();
//        String methodName = joinPoint.getSignature().getName();
//        if(!methodName.equals("sysUserLogin") && session.getAttribute("Authentication")==null){
//            throw new WisdomParkException(ResponseData.STATUS_CODE_110,"登录已过期");
//        }
    }


}
