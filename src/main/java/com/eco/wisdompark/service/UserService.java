package com.eco.wisdompark.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.eco.wisdompark.domain.dto.req.user.GetUserDto;
import com.eco.wisdompark.domain.dto.req.user.SearchUserDto;
import com.eco.wisdompark.domain.dto.req.user.UserDto;
import com.eco.wisdompark.domain.dto.req.user.UserLoginDto;
import com.eco.wisdompark.domain.dto.resp.UserLoginRespDto;
import com.eco.wisdompark.domain.dto.resp.UserSearchRespDto;
import com.eco.wisdompark.domain.model.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

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
     Integer countByDept(Integer deptId);

    /**
     * 查询人员信息列表
     * @param searchUserDto
     * @return
     * */
    UserSearchRespDto searchUserDtos(SearchUserDto searchUserDto);

    /**
     * 查询人员详情
     * @param getUserDto
     * @return
     * */
     UserDto getUser(GetUserDto getUserDto);

    /**
     * 查询人员ids
     * @param userIds
     * @return
     * */
    List<User> getUsers(List<Integer> userIds);


    /**
     * 查询人员信息
     * @param userId
     * @return
     */
    User queryByUserId(Integer userId);

    /**
     * 根据部门id查询人员信息
     * @param deptId
     * @return
     */
    List<User> getUserListByDeptId(Integer deptId);

    UserLoginRespDto login(UserLoginDto dto);

    /**
     * 查询全部用户
     *
     * */
    List<User> getAllUser();


    /**
     * 获取余额
     * */

    String getAb(String username);

    List<User> getListByQuery(SearchUserDto searchUserDto);


}
