package com.eco.wisdompark.controller;


import com.eco.wisdompark.common.dto.ResponseData;
import com.eco.wisdompark.domain.dto.req.consumeRecord.SearchConsumeRecordDto;
import com.eco.wisdompark.domain.dto.req.dept.AddLevel1DeptDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * CPU卡-消费记录表 前端控制器
 * </p>
 *
 * @author litao
 * @since 2018-12-28
 */
@RestController
@RequestMapping("api/consume-record")
@Api(value = "消费记录API", description = "消费记录API")
public class ConsumeRecordController {


    @RequestMapping(value = "/searchUserConsumeRecordDtos", method = RequestMethod.POST)
    @ApiOperation(value = "查询人员消费记录", httpMethod = "POST")
    public ResponseData searchUserConsumeRecordDtos(SearchConsumeRecordDto searchConsumeRecordDto) {
        return ResponseData.OK();
    }








}
