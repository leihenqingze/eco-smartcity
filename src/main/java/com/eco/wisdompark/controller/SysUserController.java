package com.eco.wisdompark.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.eco.wisdompark.common.aop.SysUserLogin;
import com.eco.wisdompark.common.dto.ResponseData;
import com.eco.wisdompark.common.utils.RegexUtils;
import com.eco.wisdompark.domain.dto.req.sysUser.SaveSysUserDto;
import com.eco.wisdompark.domain.dto.req.sysUser.SysUserDto;
import com.eco.wisdompark.domain.dto.req.sysUser.SysUserLoginDto;
import com.eco.wisdompark.domain.dto.req.sysUser.UpdateUserPassDto;
import com.eco.wisdompark.domain.model.SysUser;
import com.eco.wisdompark.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * <p>
 * 系统用户表 前端控制器
 * </p>
 *
 * @author litao
 * @since 2018-12-28
 */
@RestController
@RequestMapping("api/sys-user")
@Api(value = "系统用户管理API", description = "系统用户管理API")
@Slf4j
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @RequestMapping(value = "/getSysUserList", method = RequestMethod.POST)
    @ApiOperation(value = "分页查询系统用户", httpMethod = "POST")
    @SysUserLogin
    public ResponseData<IPage<SysUser>> getSysUserList(@RequestBody SysUserDto sysUserDto) {
        log.debug(">>>>>getSysUserList,param is :{}", JSON.toJSONString(sysUserDto));
        IPage<SysUser> sysUserPage = sysUserService.getSysUserPage(sysUserDto);
        return ResponseData.OK(sysUserPage);
    }

    @RequestMapping(value = "/saveSysUser", method = RequestMethod.POST)
    @ApiOperation(value = "添加系统用户", httpMethod = "POST")
    @SysUserLogin
    public ResponseData saveSysUser(@RequestBody SaveSysUserDto saveSysUserDto) {
        log.debug(">>>>>saveSysUser,param is :{}", JSON.toJSONString(saveSysUserDto));
        if (saveSysUserDto == null) {
            return ResponseData.ERROR("保存失败!");
        }
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(saveSysUserDto, sysUser);
        int result = sysUserService.saveSysUser(sysUser);
        if (result <= 0) {
            return ResponseData.ERROR("保存失败!");
        }
        return ResponseData.OK();
    }

    @RequestMapping(value = "/updateSysUserPass", method = RequestMethod.POST)
    @ApiOperation(value = "修改系统用户密码", httpMethod = "POST")
    @SysUserLogin
    public ResponseData updateSysUserPass(@RequestBody UpdateUserPassDto updateUserPassDto,HttpServletRequest request) {
        log.debug(">>>>>updateSysUserPass,updateUserPassDto:{}", JSON.toJSONString(updateUserPassDto));
        if (!StringUtils.trim(updateUserPassDto.getNewPassWord()).equals(StringUtils.trim(updateUserPassDto.getConfirmNewPassWord()))) {
            return ResponseData.ERROR("两次密码输入不一致!");
        }
        SysUser sysUser = (SysUser) request.getSession().getAttribute("Authentication");
        if(sysUser == null){
            return ResponseData.ERROR(ResponseData.STATUS_CODE_110,"登录已失效!");
        }
        int result = sysUserService.updateSysUserPass(sysUser.getId(), updateUserPassDto.getOldPassWord(), updateUserPassDto.getNewPassWord());
        if (result <= 0) {
            return ResponseData.ERROR("修改密码失败!");
        }
        return ResponseData.OK();
    }

    @RequestMapping(value = "sysUserLogin", method = RequestMethod.POST)
    @ApiOperation(value = "系统用户登录", httpMethod = "POST")
    public ResponseData sysUserLogin(HttpServletRequest request,
                                     @RequestBody SysUserLoginDto sysUserLoginDto) {
        log.debug(">>>>>sysUserLogin,sysUserLoginDto:{}", JSON.toJSONString(sysUserLoginDto));
        SysUser sysUser = null;
        if (RegexUtils.isPhone(StringUtils.trim(sysUserLoginDto.getKeyword()))) {
            sysUser = sysUserService.phoneLogin(sysUserLoginDto.getKeyword(), sysUserLoginDto.getPassWord());
        } else {
            sysUser = sysUserService.userNameLogin(sysUserLoginDto.getKeyword(), sysUserLoginDto.getPassWord());
        }
        if (sysUser == null) {
            return ResponseData.ERROR("登录失败!");
        }
        HttpSession session = request.getSession();
        session.setAttribute("Authentication", sysUser);
        session.setMaxInactiveInterval(1800); // 登录有效期30分钟
        return ResponseData.OK(sysUser.getSysUserDepartment());

    }


}
