package com.eco.wisdompark.controller;

import com.eco.wisdompark.common.dto.ResponseData;
import com.eco.wisdompark.domain.dto.req.card.ActiveCpuCardDto;
import com.eco.wisdompark.domain.dto.req.card.RechargeCardDto;
import com.eco.wisdompark.domain.dto.req.card.MakingCpuCardDto;
import com.eco.wisdompark.domain.dto.req.card.QueryCardInfoDto;
import com.eco.wisdompark.domain.dto.resp.RespActiveCpuCardDto;
import com.eco.wisdompark.domain.dto.resp.RespMakingCpuCardDto;
import com.eco.wisdompark.domain.dto.resp.RespQueryCardInfoDto;
import com.eco.wisdompark.domain.dto.resp.RespRechargeBatchDataDto;
import com.eco.wisdompark.service.CpuCardService;
import com.eco.wisdompark.service.ReturnCardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * CPU卡相关 控制器
 * </p>
 *
 * @author haihao
 * @since 2018-12-30
 */
@RestController
@RequestMapping("api/cpu-card")
@Api(value = "CPU卡相关API", description = "CPU卡相关API")
public class CpuCardController {

    @Autowired
    private CpuCardService cpuCardService;
    @Autowired
    private ReturnCardService returnCardService;

    @RequestMapping(value = "/making", method = RequestMethod.POST)
    @ApiOperation(value = "制卡接口", httpMethod = "POST")
    public ResponseData<RespMakingCpuCardDto> makingCpuCard(@RequestBody MakingCpuCardDto makingCpuCardDto) {
        RespMakingCpuCardDto respMakingCpuCardDto = cpuCardService.makingCpuCard(makingCpuCardDto);
        return ResponseData.OK(respMakingCpuCardDto);
    }


    @RequestMapping(value = "/active", method = RequestMethod.POST)
    @ApiOperation(value = "卡片激活接口", httpMethod = "POST")
    public ResponseData<RespActiveCpuCardDto> active(@RequestBody ActiveCpuCardDto activeCpuCardDto) {
        RespActiveCpuCardDto respActiveCpuCardDto = cpuCardService.activeCpuCard(activeCpuCardDto);
        return ResponseData.OK(respActiveCpuCardDto);
    }

    @RequestMapping(value = "/query", method = RequestMethod.POST)
    @ApiOperation(value = "查询卡片信息接口", httpMethod = "POST")
    public ResponseData<RespQueryCardInfoDto> queryCardInfo(@RequestBody QueryCardInfoDto queryCardInfoDto) {
        RespQueryCardInfoDto respQueryCardInfoDto = cpuCardService.queryCardInfo(queryCardInfoDto);
        return ResponseData.OK(respQueryCardInfoDto);
    }

    @RequestMapping(value = "/recharge", method = RequestMethod.POST)
    @ApiOperation(value = "卡片余额充值接口", httpMethod = "POST")
    public ResponseData<Boolean> recharge(@RequestBody RechargeCardDto rechargeCardDto) {
        Boolean rechargeResult = cpuCardService.rechargeSingle(rechargeCardDto);
        return ResponseData.OK(rechargeResult);
    }

    @RequestMapping(value = "/recharge/batch/fileUpload", method = RequestMethod.POST)
    @ApiOperation(value = "批量充值Excel文件上传接口", httpMethod = "POST")
    public ResponseData<RespRechargeBatchDataDto> fileUpload(@RequestParam("file") MultipartFile file) {
        RespRechargeBatchDataDto respRechargeBatchDataDto = cpuCardService.fileUpload(file);
        return ResponseData.OK(respRechargeBatchDataDto);
    }

    @RequestMapping(value = "/recharge/batch", method = RequestMethod.POST)
    @ApiOperation(value = "批量充值确认接口", httpMethod = "POST")
    public ResponseData<Boolean> rechargeBatch(@RequestParam String fileCode) {
        Boolean rechargeResult = cpuCardService.rechargeBatch(fileCode);
        return ResponseData.OK(rechargeResult);
    }

    @RequestMapping(value = "/returnCard", method = RequestMethod.POST)
    @ApiOperation(value = "退卡", httpMethod = "POST")
    public ResponseData<Boolean> rechargeBatch(@RequestBody QueryCardInfoDto queryCardInfoDto) {
        Boolean result = returnCardService.returnCard(queryCardInfoDto.getCardId());
        return ResponseData.OK(result);
    }

}