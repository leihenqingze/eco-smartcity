package com.eco.wisdompark.common.aop;

import com.eco.wisdompark.common.dto.ResponseData;
import com.eco.wisdompark.common.exceptions.WisdomParkException;
import com.eco.wisdompark.common.utils.TokenModel;
import com.eco.wisdompark.common.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

 /**
  * 登录拦截
  *
  * @author zhangkai
  * @date 2019/1/1 下午11:02
  */
@Aspect
@Component
@Slf4j
public class OvalLoginAdavice {

    @Autowired
    private TokenUtils tokenUtils;

    @Pointcut("@annotation(com.eco.wisdompark.common.aop.SysUserLogin)")
    public void checkLogin() {
    }

    @Before("checkLogin()")
    public void doBefore(JoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        boolean loginCheck = false;
        /*Cookie[] cookies = request.getCookies();
        if (cookies!=null) {
           for (int i = 0; i < cookies.length; i++) {
               Cookie cookie = cookies[i];
               if (cookie.getName().equals("Authentication")) {
                   String token = cookie.getValue();
                   log.info("========>set token:{}",token);
                   TokenModel tokenModel = tokenUtils.get(token);
                   // 校验token 合法
                   if (tokenUtils.check(tokenModel)) {
                       loginCheck = true;
                       break;
                   }
               }
           }
        }
        String methodName = joinPoint.getSignature().getName();
        if(!methodName.equals("sysUserLogin") && !loginCheck){
           throw new WisdomParkException(ResponseData.STATUS_CODE_110,"登录已过期");
        }*/
    }

}
