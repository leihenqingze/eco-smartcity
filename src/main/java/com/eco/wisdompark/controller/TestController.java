package com.eco.wisdompark.controller;

import com.eco.wisdompark.common.dto.ResponseData;
import com.eco.wisdompark.domain.Test;
import com.eco.wisdompark.domain.dto.req.SaveTestDto;
import com.eco.wisdompark.service.TestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author litao
 * @since 2018-10-16
 */
@RestController
@RequestMapping("api/test")
@Api(value = "硬件测试接口", description = "硬件测试接口相关API")
public class TestController {

    @Autowired
    public TestService testService;

    @RequestMapping(value = "/index", method = RequestMethod.POST)
    @ApiOperation(value = "硬件接口", httpMethod = "POST")
    public ResponseData getStr(SaveTestDto saveTestDto) {
        Test test = new Test();
        test.setName(saveTestDto.getName());
        test.setCard_id(saveTestDto.getCard_id());
        testService.save(test);
        return ResponseData.OK(1);
    }

}