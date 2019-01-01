package com.eco.wisdompark.mapper;

import com.eco.wisdompark.domain.model.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 系统用户表 Mapper 接口
 * </p>
 *
 * @author litao
 * @since 2018-12-28
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    int updateSysUserPass(@Param("sysUserId") int sysUserId,@Param("passWord") String passWord);

}
