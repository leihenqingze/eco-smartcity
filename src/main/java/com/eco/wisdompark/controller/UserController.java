package com.eco.wisdompark.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.eco.wisdompark.common.aop.SysUserLogin;
import com.eco.wisdompark.common.dto.ResponseData;
import com.eco.wisdompark.common.utils.RedisUtil;
import com.eco.wisdompark.common.utils.SendSmsUtils;
import com.eco.wisdompark.common.utils.StringTools;
import com.eco.wisdompark.domain.dto.req.card.QueryCardInfoDto;
import com.eco.wisdompark.domain.dto.req.user.*;
import com.eco.wisdompark.domain.dto.resp.RespQueryCardInfoDto;
import com.eco.wisdompark.domain.dto.resp.UserLoginRespDto;
import com.eco.wisdompark.domain.dto.resp.UserSearchRespDto;
import com.eco.wisdompark.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * 人员表 前端控制器
 * </p>
 *
 * @author litao
 * @since 2018-12-28
 */
@RestController
@RequestMapping("api/user")
@Api(value = "人员相关API", description = "人员相关API")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisUtil redisUtil;

    @RequestMapping(value = "/searchUserDtos", method = RequestMethod.POST)
    @ApiOperation(value = "查询人员列表", httpMethod = "POST")
    @SysUserLogin
    public ResponseData<UserSearchRespDto> searchUserDtos(@RequestBody SearchUserDto searchUserDto) {
        UserSearchRespDto result = userService.searchUserDtos(searchUserDto);
        return ResponseData.OK(result);
    }

    @RequestMapping(value = "/updateUserInfo", method = RequestMethod.POST)
    @ApiOperation(value = "修改人员信息", httpMethod = "POST")
    public ResponseData<Integer> updateUserInfo(@RequestBody UpdateUserInfoDto updateUserInfoDto) {
        Integer result = userService.updateUserInfo(updateUserInfoDto);
        return ResponseData.OK(result);
    }


    @RequestMapping(value = "/getUser", method = RequestMethod.POST)
    @ApiOperation(value = "查询人员详情", httpMethod = "POST")
    public ResponseData<UserDto> getUser(@RequestBody GetUserDto getUserDto) {
        UserDto result = userService.getUser(getUserDto);
        return ResponseData.OK(result);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ApiOperation(value = "人员登录", httpMethod = "POST")
    public ResponseData<UserLoginRespDto> login(@RequestBody UserLoginDto dto) {
        UserLoginRespDto result = userService.login(dto);
        return ResponseData.OK(result);
    }

    @RequestMapping(value = "/sendSms", method = RequestMethod.POST)
    @ApiOperation(value = "发送验证码", httpMethod = "POST")
    public ResponseData sendSms(@RequestBody SmsDto smsDto) {
        try {
            String tel=smsDto.getPhoneNum();
            if(StringUtils.isBlank(tel)){
               return  ResponseData.ERROR(400,"请正确填写手机号码");
            }
            Pattern p = Pattern.compile("\\d{11}$");
            Matcher m = p.matcher(tel);
            if (!m.matches()) {
                return  ResponseData.ERROR(400,"请正确填写手机号码");
            }
            String sRand = "";
            Random random = new Random();
            for (int i = 0; i < 6; i++) {
                String rand = String.valueOf(random.nextInt(10));
                sRand += rand;
            }
            SendSmsUtils.sendSms(tel,sRand);
            redisUtil.set(tel,sRand,300);
            return ResponseData.OK("验证码已经发送");

        }catch (Exception e){
            return  ResponseData.ERROR(400,"短信发送异常");
        }
    }

}