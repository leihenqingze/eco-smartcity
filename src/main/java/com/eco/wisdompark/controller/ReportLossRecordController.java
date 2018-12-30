package com.eco.wisdompark.controller;


import com.eco.wisdompark.common.dto.ResponseData;
import com.eco.wisdompark.domain.dto.req.card.QueryCardInfoDto;
import com.eco.wisdompark.domain.dto.req.card.ReissueCardDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 挂失记录 前端控制器
 * </p>
 *
 * @author litao
 * @since 2018-12-28
 */
@RestController
@RequestMapping("/report-loss-record")
@Api(value = "CPU卡挂失/补卡相关API", description = "CPU卡挂失/补卡相关API")
public class ReportLossRecordController {

    @RequestMapping(value = "/query", method = RequestMethod.POST)
    @ApiOperation(value = "卡片挂失查询接口", httpMethod = "POST")
    public ResponseData queryCardInfo(@RequestBody QueryCardInfoDto queryCardInfoDto) {
        return ResponseData.OK();
    }

    @RequestMapping(value = "/reissue", method = RequestMethod.POST)
    @ApiOperation(value = "卡片挂失补发接口", httpMethod = "POST")
    public ResponseData reissueCard(@RequestBody ReissueCardDto reissueCardDto) {
        return ResponseData.OK();
    }

}
