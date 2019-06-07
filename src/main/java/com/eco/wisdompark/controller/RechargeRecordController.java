package com.eco.wisdompark.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.eco.wisdompark.common.aop.SysUserLogin;
import com.eco.wisdompark.common.dto.ResponseData;
import com.eco.wisdompark.domain.dto.req.consumeRecord.SearchConsumeRecordDto;
import com.eco.wisdompark.domain.dto.req.rechargeRecord.RechargeRecordDto;
import com.eco.wisdompark.domain.dto.req.rechargeRecord.SearchRechargeRecordDto;
import com.eco.wisdompark.service.RechargeRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * CPU卡-充值记录表 前端控制器
 * </p>
 *
 * @author litao
 * @since 2018-12-28
 */
@RestController
@RequestMapping("api/recharge-record")
@Api(value = "充值记录API", description = "充值记录API")
public class RechargeRecordController {
    @Autowired
    private RechargeRecordService rechargeRecordService;


    @RequestMapping(value = "/searchUserRechargeRecordDtos", method = RequestMethod.POST)
    @ApiOperation(value = "查询人员充值记录", httpMethod = "POST")
    @SysUserLogin
    public ResponseData searchUserRechargeRecordDtos( @RequestBody SearchRechargeRecordDto searchRechargeRecordDto) {
        IPage<RechargeRecordDto> result = rechargeRecordService.searchUserRechargeRecordDtos(searchRechargeRecordDto);
        return ResponseData.OK(result);
    }

}
