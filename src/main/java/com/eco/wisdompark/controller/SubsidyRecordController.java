package com.eco.wisdompark.controller;

import com.eco.wisdompark.common.dto.ResponseData;
import com.eco.wisdompark.domain.dto.req.consumeRecord.SearchConsumeRecordDto;
import com.eco.wisdompark.domain.dto.req.subsidy.SearchAutoSubsidyRecordReq;
import com.eco.wisdompark.domain.dto.resp.SubsidyDetailsDto;
import com.eco.wisdompark.service.SubsidyRecordService;
import com.eco.wisdompark.domain.dto.req.subsidyRecord.SubsidyRecordDto;
import com.eco.wisdompark.domain.model.SubsidyRecord;
import com.eco.wisdompark.service.SubsidyRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * CPU卡-补助记录表 前端控制器
 * </p>
 *
 * @author litao
 * @since 2018-12-28
 */
@RestController
@RequestMapping("api/subsidy-record")
@Api(value = "补助记录API", description = "补助记录API")
public class SubsidyRecordController {

    @Autowired
    private SubsidyRecordService subsidyRecordService;

    @RequestMapping(value = "/searchUserSubsidyRecordDtos", method = RequestMethod.POST)
    @ApiOperation(value = "查询人员补助记录", httpMethod = "POST")
    public ResponseData<IPage<SubsidyRecordDto>> searchUserSubsidyRecordDtos(SearchConsumeRecordDto searchConsumeRecordDto) {
         IPage<SubsidyRecordDto> result= subsidyRecordService.searchUserSubsidyRecordDtos(searchConsumeRecordDto);
        return ResponseData.OK(result);
    }

    @RequestMapping(value = "/searchSubsidyRecordByRuleId", method = RequestMethod.POST)
    @ApiOperation(value = "查询自动补助记录", httpMethod = "POST")
    public ResponseData<SubsidyDetailsDto> searchDeptSubsidyRecord(
            @RequestBody SearchAutoSubsidyRecordReq searchAutoSubsidyRecordReq) {
        return ResponseData.OK(subsidyRecordService.searchDeptSubsidyRecord(searchAutoSubsidyRecordReq));
    }

}