package com.eco.wisdompark.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.eco.wisdompark.common.dto.ResponseData;
import com.eco.wisdompark.domain.dto.req.user.GetUserDto;
import com.eco.wisdompark.domain.dto.req.user.SearchUserDto;
import com.eco.wisdompark.domain.dto.req.user.UserDto;
import com.eco.wisdompark.domain.model.User;
import com.eco.wisdompark.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping(value = "/searchUserDtos", method = RequestMethod.POST)
    @ApiOperation(value = "查询人员列表", httpMethod = "POST")
    public ResponseData<IPage<UserDto>> searchUserDtos( @RequestBody SearchUserDto searchUserDto) {
        IPage<UserDto> result=userService.searchUserDtos(searchUserDto);
        return ResponseData.OK(result);
    }

    @RequestMapping(value = "/getUser", method = RequestMethod.POST)
    @ApiOperation(value = "查询人员详情", httpMethod = "POST")
    public ResponseData<UserDto> getUser( @RequestBody GetUserDto getUserDto) {
        UserDto result=userService.getUser(getUserDto);
        return ResponseData.OK(result);
    }


}
