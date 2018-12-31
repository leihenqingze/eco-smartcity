package com.eco.wisdompark.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eco.wisdompark.domain.dto.req.user.SearchUserDto;
import com.eco.wisdompark.domain.dto.req.user.UserDto;
import com.eco.wisdompark.domain.model.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 人员表 服务类
 * </p>
 *
 * @author litao
 * @since 2018-12-28
 */
public interface UserService extends IService<User> {


    /**
     * 查询组织架构Id的所有人员数量
     * @param deptId
     * @return
     * */
    public Integer countByDept(Integer deptId);

    /**
     * 查询人员信息列表
     * @param searchUserDto
     * @return
     * */
    public IPage<UserDto> searchUserDtos(SearchUserDto searchUserDto);

}
