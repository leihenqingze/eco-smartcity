package com.eco.wisdompark.controller;


import com.eco.wisdompark.common.dto.ResponseData;
import com.eco.wisdompark.domain.dto.req.card.RechargeCardDto;
import com.eco.wisdompark.domain.dto.req.card.MakingCpuCardDto;
import com.eco.wisdompark.domain.dto.req.card.QueryCardInfoDto;
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
 * CPU卡 前端控制器
 * </p>
 *
 * @author litao
 * @since 2018-12-28
 */
@RestController
@RequestMapping("/cpu-card")
@Api(value = "CPU卡相关API", description = "CPU卡相关API")
public class CpuCardController {

    @Autowired
    private CpuCardService cpuCardService;

    @RequestMapping(value = "/making", method = RequestMethod.POST)
    @ApiOperation(value = "制卡/卡片激活接口", httpMethod = "POST")
    public ResponseData makingCpuCard(@RequestBody MakingCpuCardDto makingCpuCardDto) {
        RespMakingCpuCardDto respMakingCpuCardDto = cpuCardService.makingCpuCard(makingCpuCardDto);
        return ResponseData.OK(respMakingCpuCardDto);
    }

    @RequestMapping(value = "/query", method = RequestMethod.POST)
    @ApiOperation(value = "查询卡片信息接口", httpMethod = "POST")
    public ResponseData queryCardInfo(@RequestBody QueryCardInfoDto queryCardInfoDto) {
        RespQueryCardInfoDto respQueryCardInfoDto = cpuCardService.queryCardInfo(queryCardInfoDto);
        return ResponseData.OK(respQueryCardInfoDto);
    }

    @RequestMapping(value = "/recharge", method = RequestMethod.POST)
    @ApiOperation(value = "卡片余额充值接口", httpMethod = "POST")
    public ResponseData recharge(@RequestBody RechargeCardDto rechargeCardDto) {
        Boolean rechargeResult = cpuCardService.rechargeSingle(rechargeCardDto);
        return ResponseData.OK(rechargeResult);
    }

    @RequestMapping(value = "/recharge/batch", method = RequestMethod.POST)
    @ApiOperation(value = "批量充值接口", httpMethod = "POST")
    public ResponseData rechargeBatch(@RequestParam("file") MultipartFile file) {
        String fileName = file.getOriginalFilename();
//        try {
//            a = testService.batchImport(fileName, file);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return ResponseData.OK();
    }




}
