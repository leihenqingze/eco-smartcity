package com.eco.wisdompark.controller;


import com.eco.wisdompark.common.dto.ResponseData;
import com.eco.wisdompark.domain.dto.req.user.SearchUserDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
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

    @RequestMapping(value = "/searchUserDtos", method = RequestMethod.POST)
    @ApiOperation(value = "查询人员列表", httpMethod = "POST")
    public ResponseData searchUserDtos(SearchUserDto searchUserDto) {
        return ResponseData.OK();
    }

    @RequestMapping(value = "/getUser", method = RequestMethod.POST)
    @ApiOperation(value = "查询人员详情", httpMethod = "POST")
    @ApiImplicitParam(name = "id", value = "人员Id",  dataType = "Integer")
    public ResponseData getUser(Integer id) {
        return ResponseData.OK();
    }


}