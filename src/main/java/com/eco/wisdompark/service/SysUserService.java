package com.eco.wisdompark.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.eco.wisdompark.domain.dto.req.sysUser.SysUserDto;
import com.eco.wisdompark.domain.model.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 系统用户表 服务类
 * </p>
 *
 * @author litao
 * @since 2018-12-28
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 分页查询系统用户信息
     * @param sysUserDto 查询条件
     * @return 系统用户分页信息
     */
    IPage<SysUser> getSysUserPage(SysUserDto sysUserDto);

    /**
     * 保存系统用户信息
     * @param sysUser 系统用户信息
     * @return
     */
    int saveSysUser(SysUser sysUser);

    /**
     * 修改系统用户密码
     * @param sysUserId 系统用户id
     * @param oldPassWord 旧密码
     * @param passWord 新密码
     * @return
     */
    int updateSysUserPass(int sysUserId,String oldPassWord,String passWord);

    /**
     * 根据用户名查询系统用户
     * @param sysUserName 系统用户名
     * @return
     */
    SysUser getSysUserByUserName(String sysUserName);

    /**
     * 根据手机号查询系统用户
     * @param phone 手机号
     * @return
     */
    SysUser getSysUserByPhone(String phone);

    /**
     * 手机号登录
     * @param phone 手机号
     * @param passWord 密码
     * @return
     */
    SysUser phoneLogin(String phone,String passWord);

    /**
     * 用户名登录
     * @param userName 用户名
     * @param passWord  密码
     * @return
     */
    SysUser userNameLogin(String userName,String passWord);

}
