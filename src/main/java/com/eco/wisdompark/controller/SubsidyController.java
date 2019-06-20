package com.eco.wisdompark.controller;


import com.eco.wisdompark.common.aop.SysUserLogin;
import com.eco.wisdompark.common.dto.ResponseData;
import com.eco.wisdompark.domain.dto.req.subsidy.ManualSubsidyDto;
import com.eco.wisdompark.service.SubsidyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 发放补助 前端控制器
 * </p>
 *
 * @author litao
 * @since 2018-12-28
 */
@RestController
@RequestMapping("api/subsidy")
@Api(value = "发放补助API", description = "发放补助API")
public class SubsidyController {

    @Autowired
    private SubsidyService subsidyService;

    @RequestMapping(value = "/manualSubsidy", method = RequestMethod.POST)
    @ApiOperation(value = "手动补助", httpMethod = "POST")
    @SysUserLogin
    public ResponseData manualSubsidy(@RequestBody ManualSubsidyDto manualSubsidyDto) {
        subsidyService.manualSubsidy(manualSubsidyDto);
        return ResponseData.OK();
    }

    @RequestMapping(value = "/automaticSubsidy", method = RequestMethod.POST)
    @ApiOperation(value = "自动补助", httpMethod = "POST")
    public ResponseData automaticSubsidy() {
        subsidyService.automaticSubsidy();
        return ResponseData.OK();
    }

    @RequestMapping(value = "/subsidy/batch", method = RequestMethod.POST)
    @ApiOperation(value = "批量补助接口", httpMethod = "POST")
//    @SysUserLogin
    public ResponseData<Boolean> rechargeBatch(@RequestParam("file") MultipartFile file) {
        return ResponseData.OK(subsidyService.batchImportSubsidy(file));
    }

}
