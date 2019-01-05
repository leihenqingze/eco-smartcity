package com.eco.wisdompark.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eco.wisdompark.common.dto.ResponseData;
import com.eco.wisdompark.common.exceptions.WisdomParkException;
import com.eco.wisdompark.domain.dto.req.sysUser.SysUserDto;
import com.eco.wisdompark.domain.model.SysUser;
import com.eco.wisdompark.mapper.SysUserMapper;
import com.eco.wisdompark.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;

import java.util.List;

/**
 * <p>
 * 系统用户表 服务实现类
 * </p>
 *
 * @author litao
 * @since 2018-12-28
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    private static final String default_sysuser_password = "123456";

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public IPage<SysUser> getSysUserPage(SysUserDto sysUserDto) {

        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        if(sysUserDto.getId() != null){
            wrapper.eq("id", sysUserDto.getId());
        }
        if (sysUserDto.getSysUserDepartment() != null) {
            wrapper.eq("sys_user_department", sysUserDto.getSysUserDepartment());
        }
        if (StringUtils.isNotBlank(sysUserDto.getSysUserName())) {
            wrapper.like("sys_user_name", sysUserDto.getSysUserName());
        }
        if (StringUtils.isNotBlank(sysUserDto.getSysUserPhone())) {
            wrapper.like("sys_user_phone", sysUserDto.getSysUserPhone());
        }
        IPage<SysUser> sysUserPage = baseMapper.selectPage(new Page<>(sysUserDto.getCurrentPage(), sysUserDto.getPageSize()), wrapper);
        if(sysUserPage == null){
            sysUserPage = new Page<>(sysUserDto.getCurrentPage(),sysUserDto.getPageSize());
            sysUserPage.setRecords(Lists.newArrayList());
        }
        return sysUserPage;
    }

    @Override
    public int saveSysUser(SysUser sysUser) {
        if(sysUser == null){
            throw new WisdomParkException(ResponseData.STATUS_CODE_400, "系统用户不能为空");
        }
        if(StringUtils.isBlank(sysUser.getSysUserName())){
            throw new WisdomParkException(ResponseData.STATUS_CODE_400,"系统用户名不能为空");
        }
        if(StringUtils.isBlank(sysUser.getSysUserPhone())){
            throw new WisdomParkException(ResponseData.STATUS_CODE_400,"系统用户手机号不能为空");
        }
        if(sysUser.getSysUserDepartment() == null){
            throw new WisdomParkException(ResponseData.STATUS_CODE_400,"系统用户所属部门不能为空");
        }

        QueryWrapper<SysUser> userNameWrapper = new QueryWrapper<>();
        userNameWrapper.eq("sys_user_name", sysUser.getSysUserName());
        List<SysUser> sysUserListByUserName = baseMapper.selectList(userNameWrapper);
        if(!CollectionUtils.isEmpty(sysUserListByUserName)){
            throw new WisdomParkException(ResponseData.STATUS_CODE_400,"用户名已存在");
        }

        QueryWrapper<SysUser> userPhoneWrapper = new QueryWrapper<>();
        userPhoneWrapper.eq("sys_user_phone", sysUser.getSysUserPhone());
        List<SysUser> sysUserListByUserPhone = baseMapper.selectList(userPhoneWrapper);
        if(!CollectionUtils.isEmpty(sysUserListByUserPhone)){
            throw new WisdomParkException(ResponseData.STATUS_CODE_400,"手机号已存在");
        }

        String sysUsePw = sysUser.getSysUserPass();
        if(StringUtils.isBlank(sysUsePw)){
            sysUsePw = default_sysuser_password;
            sysUser.setSysUserPass(DigestUtils.md5DigestAsHex(sysUsePw.getBytes()));
        }
        return baseMapper.insert(sysUser);
    }

    @Override
    public int updateSysUserPass(int sysUserId,String oldPassWord,String passWord) {
        if(sysUserId <= 0){
            throw new WisdomParkException(ResponseData.STATUS_CODE_400,"系统用户id不能为空");
        }
        if(StringUtils.isBlank(oldPassWord)){
            throw new WisdomParkException(ResponseData.STATUS_CODE_400,"旧密码不能为空");
        }
        if(StringUtils.isBlank(passWord)){
            throw new WisdomParkException(ResponseData.STATUS_CODE_400,"新密码不能为空");
        }
        SysUser sysUser = baseMapper.selectById(sysUserId);
        if(sysUser == null){
            throw new WisdomParkException(ResponseData.STATUS_CODE_400,"系统用户不存在");
        }
        if(!sysUser.getSysUserPass().equals(DigestUtils.md5DigestAsHex(StringUtils.trim(oldPassWord).getBytes()))){
            throw new WisdomParkException(ResponseData.STATUS_CODE_400,"旧密码输入错误");
        }
        return sysUserMapper.updateSysUserPass(sysUserId,DigestUtils.md5DigestAsHex(passWord.getBytes()));
    }

    @Override
    public SysUser getSysUserByUserName(String sysUserName) {
        if(StringUtils.isBlank(sysUserName)){
            return null;
        }
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("sys_user_name", sysUserName);
        List<SysUser> sysUserList = baseMapper.selectList(wrapper);
        if(CollectionUtils.isEmpty(sysUserList)){
            return null;
        }
        return sysUserList.get(0);
    }

    @Override
    public SysUser getSysUserByPhone(String phone) {
        if(StringUtils.isBlank(phone)){
            return null;
        }
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("sys_user_phone", phone);
        List<SysUser> sysUserList = baseMapper.selectList(wrapper);
        if(CollectionUtils.isEmpty(sysUserList)){
            return null;
        }
        return sysUserList.get(0);
    }

    @Override
    public SysUser phoneLogin(String phone, String passWord) {
        if(StringUtils.isBlank(phone)){
            throw new WisdomParkException(ResponseData.STATUS_CODE_400,"手机号不能为空");
        }
        if(StringUtils.isBlank(passWord)){
            throw new WisdomParkException(ResponseData.STATUS_CODE_400,"密码不能为空");
        }
        SysUser sysUser = getSysUserByPhone(phone);
        if(sysUser == null){
            throw new WisdomParkException(ResponseData.STATUS_CODE_400,"手机号不存在");
        }
        if(!sysUser.getSysUserPass().equals(DigestUtils.md5DigestAsHex(StringUtils.trim(passWord).getBytes()))){
            throw new WisdomParkException(ResponseData.STATUS_CODE_400,"密码不正确");
        }
        return sysUser;
    }

    @Override
    public SysUser userNameLogin(String userName, String passWord) {
        if(StringUtils.isBlank(userName)){
            throw new WisdomParkException(ResponseData.STATUS_CODE_400,"用户名不能为空");
        }
        if(StringUtils.isBlank(passWord)){
            throw new WisdomParkException(ResponseData.STATUS_CODE_400,"密码不能为空");
        }
        SysUser sysUser = getSysUserByUserName(userName);
        if(sysUser == null){
            throw new WisdomParkException(ResponseData.STATUS_CODE_400,"用户名不存在");
        }
        if(!sysUser.getSysUserPass().equals(DigestUtils.md5DigestAsHex(StringUtils.trim(passWord).getBytes()))){
            throw new WisdomParkException(ResponseData.STATUS_CODE_400,"密码不正确");
        }
        return sysUser;
    }
}
