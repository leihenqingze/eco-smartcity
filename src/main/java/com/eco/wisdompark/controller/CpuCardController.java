package com.eco.wisdompark.controller;


import com.eco.wisdompark.common.dto.ResponseData;
import com.eco.wisdompark.domain.dto.req.card.ActiveCpuCardDto;
import com.eco.wisdompark.domain.dto.req.card.RechargeCardDto;
import com.eco.wisdompark.domain.dto.req.card.MakingCpuCardDto;
import com.eco.wisdompark.domain.dto.req.card.QueryCardInfoDto;
import com.eco.wisdompark.domain.dto.resp.RespActiveCpuCardDto;
import com.eco.wisdompark.domain.dto.resp.RespMakingCpuCardDto;
import com.eco.wisdompark.domain.dto.resp.RespQueryCardInfoDto;
import com.eco.wisdompark.service.CpuCardService;
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

//    @RequestMapping(value = "/recharge/batch/upload", method = RequestMethod.POST)
//    @ApiOperation(value = "批量充值接口", httpMethod = "POST")
//    public ResponseData rechargeBatch(@RequestParam("file") MultipartFile file) {
//        Boolean rechargeResult = cpuCardService.rechargeBatch(file);
//        return ResponseData.OK();
//    }
//
//    @RequestMapping(value = "/recharge/batch", method = RequestMethod.POST)
//    @ApiOperation(value = "批量充值接口", httpMethod = "POST")
//    public ResponseData<Boolean> rechargeBatch(@RequestParam("file") MultipartFile file) {
////        Boolean rechargeResult = cpuCardService.rechargeBatch(file);
//        return ResponseData.OK();
//    }


}
