package com.eco.wisdompark.controller;


import com.eco.wisdompark.domain.Test;
import com.eco.wisdompark.service.TestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author litao
 * @since 2018-10-16
 */
@RestController
@RequestMapping("api/test")
@Api(value = "硬件测试接口",description = "硬件测试接口相关API")
public class TestController {

    @Autowired
    public TestService testService;

    @ResponseBody
    @RequestMapping("/index")
    @ApiOperation(value = "硬件接口",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value = "CUP卡的物理Id",required = true,dataType = "String"),
            @ApiImplicitParam(name="phone",value = "手机号",required = true,dataType = "String")
    })
    public  String getStr(@RequestParam String id, @RequestParam String phone){
        Test t=new Test();
        t.setName(id+phone);
        testService.save(t);
        return "sucess";

    }



}
