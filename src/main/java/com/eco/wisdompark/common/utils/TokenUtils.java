package com.eco.wisdompark.common.utils;

import com.eco.wisdompark.domain.model.SysUser;
import com.eco.wisdompark.service.SysUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Component
public class TokenUtils{
    
    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private SysUserService sysUserService;

    private static final int token_expire = 30*60; // token有效期

    public String create(Integer userId) {
        String token = UUID.randomUUID().toString().replace("-", "");
        redisUtil.set(userId == null ? null : String.valueOf(userId), token, token_expire);
        return userId.toString()+"_"+token;
    }

    public boolean check(TokenModel model) {
        boolean result = false;
        if(model != null) {
            String userId = model.getUserId().toString();
            String token = model.getToken();
            if(redisUtil.get(userId) != null){
                String authenticatedToken = redisUtil.get(userId).toString();
                if(authenticatedToken != null && authenticatedToken.equals(token)) {
                    redisUtil.expire(userId, token_expire);
                    result = true;
                }
            }
        }
        return result;
    }

    public TokenModel get(String authStr) {
        TokenModel model = null;
        if(StringUtils.isNotEmpty(authStr)) {
            String[] modelArr = authStr.split("_");
            if(modelArr.length == 2) {
                int userId = Integer.parseInt(modelArr[0]);
                String token = modelArr[1];
                model = new TokenModel(userId, token);
            }
        }
        return model;
    }

    public SysUser getLoginSysUser(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Cookie[] cookies = request.getCookies();
        if (cookies!=null) {
            for (int i = 0; i < cookies.length; i++) {
                Cookie cookie = cookies[i];
                if (cookie.getName().equals("Authentication")) {
                    String token = cookie.getValue();
                    TokenModel tokenModel = get(token);
                    Integer userId = tokenModel.getUserId();
                    if(userId != null){
                        return sysUserService.getById(userId);
                    }
                }
            }
        }
        return null;
    }

}