package com.eco.wisdompark.controller;


import com.eco.wisdompark.common.dto.ResponseData;
import com.eco.wisdompark.domain.dto.req.card.LossQueryCardInfoDto;
import com.eco.wisdompark.domain.dto.req.card.LossQueryConfirmDto;
import com.eco.wisdompark.domain.dto.req.card.QueryCardInfoDto;
import com.eco.wisdompark.domain.dto.req.card.ReissueCardDto;
import com.eco.wisdompark.domain.dto.resp.RespLossQueryConfirmDto;
import com.eco.wisdompark.domain.dto.resp.RespQueryCardInfoDto;
import com.eco.wisdompark.domain.dto.resp.RespQueryCardInfoListDto;
import com.eco.wisdompark.domain.dto.resp.RespReissueCardDto;
import com.eco.wisdompark.service.CpuCardService;
import com.eco.wisdompark.service.ReportLossRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 挂失记录 前端控制器
 * </p>
 *
 * @author haihao
 * @since 2018-12-31
 */
@RestController
@RequestMapping("api/report-loss-record")
@Api(value = "CPU卡挂失/补卡相关API", description = "CPU卡挂失/补卡相关API")
public class ReportLossRecordController {

    @Autowired
    private CpuCardService cpuCardService;

    @Autowired
    private ReportLossRecordService reportLossRecordService;

    @RequestMapping(value = "/query", method = RequestMethod.POST)
    @ApiOperation(value = "卡片挂失查询接口", httpMethod = "POST")
    public ResponseData<RespQueryCardInfoListDto> lossQueryCardInfo(@RequestBody LossQueryCardInfoDto lossQueryCardInfoDto) {
        RespQueryCardInfoListDto respQueryCardInfoListDto = cpuCardService.queryCardInfoList(lossQueryCardInfoDto);
        return ResponseData.OK(respQueryCardInfoListDto);
    }


    @RequestMapping(value = "/query/confirm", method = RequestMethod.POST)
    @ApiOperation(value = "卡片挂失查询确认接口", httpMethod = "POST")
    public ResponseData<RespLossQueryConfirmDto> lossQueryConfirm(@RequestBody LossQueryConfirmDto lossQueryConfirmDto) {
        RespLossQueryConfirmDto respLossQueryConfirmDto = cpuCardService.queryCardInfo(lossQueryConfirmDto);
        return ResponseData.OK(respLossQueryConfirmDto);
    }


    @RequestMapping(value = "/reissue", method = RequestMethod.POST)
    @ApiOperation(value = "卡片挂失补发接口", httpMethod = "POST")
    public ResponseData<Boolean> reissueCard(@RequestBody ReissueCardDto reissueCardDto) {
        boolean ressueResult = reportLossRecordService.reissueCard(reissueCardDto);
        return ResponseData.OK(ressueResult);
    }

}
