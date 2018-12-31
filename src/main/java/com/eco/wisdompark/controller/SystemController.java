package com.eco.wisdompark.controller;

import com.eco.wisdompark.common.dto.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/system")
@Api(value = "系统参数相关接口", description = "系统参数相关接口")
public class SystemController {

    @RequestMapping(value = "/now", method = RequestMethod.POST)
    @ApiOperation(value = "获取当前时间(毫秒)", httpMethod = "POST")
    public ResponseData<Long> consume() {
        return ResponseData.OK(System.currentTimeMillis());
    }

}
