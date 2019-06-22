package com.eco.wisdompark.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.eco.wisdompark.common.aop.SysUserLogin;
import com.eco.wisdompark.common.dto.ResponseData;
import com.eco.wisdompark.domain.dto.req.PageReqDto;
import com.eco.wisdompark.domain.dto.req.consumeRecord.SearchConsumeRecordDto;
import com.eco.wisdompark.domain.dto.req.subsidy.SearchAutoSubsidyRecordReq;
import com.eco.wisdompark.domain.dto.req.subsidyRecord.SearchSubsidyRecordDto;
import com.eco.wisdompark.domain.dto.resp.ManualSubsidyRecordListRespDto;
import com.eco.wisdompark.domain.dto.resp.SubsidyDetailsDto;
import com.eco.wisdompark.service.SubsidyRecordService;
import com.eco.wisdompark.domain.dto.req.subsidyRecord.SubsidyRecordDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

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
    @SysUserLogin
    public ResponseData<IPage<SubsidyRecordDto>> searchUserSubsidyRecordDtos(@RequestBody SearchConsumeRecordDto searchConsumeRecordDto) {
        IPage<SubsidyRecordDto> result = subsidyRecordService.searchUserSubsidyRecordDtos(searchConsumeRecordDto);
        return ResponseData.OK(result);
    }

    @RequestMapping(value = "/searchAutoSubsidyRecord", method = RequestMethod.POST)
    @ApiOperation(value = "查询自动补助记录", httpMethod = "POST")
    @SysUserLogin
    public ResponseData<SubsidyDetailsDto> searchAutoSubsidyRecord(
            @RequestBody SearchAutoSubsidyRecordReq searchAutoSubsidyRecordReq) {
        return ResponseData.OK(subsidyRecordService.searchAutoSubsidyRecord(searchAutoSubsidyRecordReq));
    }

    @RequestMapping(value = "/searchManualSubsidyRecord", method = RequestMethod.POST)
    @ApiOperation(value = "查询手动补助记录", httpMethod = "POST")
    @SysUserLogin
    public ResponseData<List<ManualSubsidyRecordListRespDto>> searchManualSubsidyRecord(@RequestBody PageReqDto<Integer> pageReqDto) {
        return ResponseData.OK(subsidyRecordService.searchManualSubsidyRecord(pageReqDto));
    }

    @RequestMapping(value = "/searchSubsidyRecord", method = RequestMethod.POST)
    @ApiOperation(value = "查询补助记录", httpMethod = "POST")
    @SysUserLogin
    public ResponseData<IPage<SubsidyRecordDto>> searchSubsidyRecordDtos(@RequestBody SearchSubsidyRecordDto searchSubsidyRecordDto) {
        return ResponseData.OK(subsidyRecordService.searchSubsidyRecordDtos(searchSubsidyRecordDto));
    }

    @RequestMapping(value = "/exportSubsidyRecord", method = RequestMethod.POST)
    @ApiOperation(value = "导出人员补助记录", httpMethod = "POST")
    @SysUserLogin
    public void exportShopPosConsumeRecordDtos(@RequestBody SearchSubsidyRecordDto searchSubsidyRecordDto,
                                               HttpServletResponse response) {
        subsidyRecordService.exportSearchSubsidyRecord(searchSubsidyRecordDto, response);
    }

    @RequestMapping(value = "/countSubsidyRecord", method = RequestMethod.POST)
    @ApiOperation(value = "统计补助金额", httpMethod = "POST")
    @SysUserLogin
    public ResponseData<Double> countSubsidyRecordDtos(@RequestBody SearchSubsidyRecordDto searchSubsidyRecordDto) {
        return ResponseData.OK(subsidyRecordService.countSubsidyRecord(searchSubsidyRecordDto));
    }

}