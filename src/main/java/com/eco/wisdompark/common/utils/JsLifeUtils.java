package com.eco.wisdompark.common.utils;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;

/**
 * 捷汇通平台接口工具类
 *
 * @author zhangkai
 * @date 2019?05?18? 20:07
 */
@Component
@Slf4j
public class JsLifeUtils {

    private static final String jslife_log_url = "http://www.jslife.com.cn/jsaims/login";

    private static final String customer_id = "000000008002303";

    private static final String user_name = "000000008002303";

    private static final String pass_word = "000000008002303";

    @Autowired
    private RedisUtil redisUtil;

    public String getLoginToken(){

        String token = "";

        Map<String,Object> clientMap = new HashMap<>();
        clientMap.put("cid",customer_id);
        clientMap.put("usr",user_name);
        clientMap.put("psw",pass_word);
        try{
            String result = HttpClient.doPost(jslife_log_url,clientMap);
            log.info(">>>>>>>>>>jsLogin_result:{}",result);
            JSONObject loginResultJb = JSONObject.parseObject(result);
            if(loginResultJb == null){
                log.error(">>>>>>>>jsLogin_result error,jsonObject is null!");
                return token;

            }
            if(loginResultJb.get("token") == null){
                log.error(">>>>>>>>jsLogin_result error,token is null!");
                return token;
            }
            token = loginResultJb.get("token").toString();
            if(StringUtils.isBlank(token)){
                log.error(">>>>>>>>jsLogin_result error,token is empty!");
                return token;
            }
            redisUtil.set("js_token",token,90*60); // 第三方token有效期为2小时

        }catch (Exception e){
            e.printStackTrace();
            log.error(">>>>>>>>jsLogin has exception,e:{}",e.getMessage());
        }
        return token;
    }
}
