package com.eco.wisdompark.controller;

import com.eco.wisdompark.common.aop.SysUserLogin;
import com.eco.wisdompark.common.dto.ResponseData;
import com.eco.wisdompark.common.utils.StringTools;
import com.eco.wisdompark.domain.dto.req.card.*;
import com.eco.wisdompark.domain.dto.req.user.GetUserDto;
import com.eco.wisdompark.domain.dto.req.user.SerianNoDto;
import com.eco.wisdompark.domain.dto.req.user.UpdateUserBalanceDto;
import com.eco.wisdompark.domain.dto.resp.*;
import com.eco.wisdompark.service.CpuCardService;
import com.eco.wisdompark.service.ReturnCardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

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
    @SysUserLogin
    public ResponseData<RespMakingCpuCardDto> makingCpuCard(@RequestBody MakingCpuCardDto makingCpuCardDto) {
        RespMakingCpuCardDto respMakingCpuCardDto = cpuCardService.makingCpuCard(makingCpuCardDto);
        return ResponseData.OK(respMakingCpuCardDto);
    }


    @RequestMapping(value = "/active", method = RequestMethod.POST)
    @ApiOperation(value = "卡片激活接口", httpMethod = "POST")
    @SysUserLogin
    public ResponseData<RespActiveCpuCardDto> active(@RequestBody ActiveCpuCardDto activeCpuCardDto) {
        RespActiveCpuCardDto respActiveCpuCardDto = cpuCardService.activeCpuCard(activeCpuCardDto);
        return ResponseData.OK(respActiveCpuCardDto);
    }

    @RequestMapping(value = "/query", method = RequestMethod.POST)
    @ApiOperation(value = "查询卡片信息接口", httpMethod = "POST")
    public ResponseData<RespQueryCardInfoDto> queryCardInfo(@RequestBody QueryCardInfoDto queryCardInfoDto) {
        queryCardInfoDto.setCardId(StringTools.cardDecimalToHexString(queryCardInfoDto.getCardId()));
        RespQueryCardInfoDto respQueryCardInfoDto = cpuCardService.queryCardInfo(queryCardInfoDto);
        return ResponseData.OK(respQueryCardInfoDto);
    }

    @RequestMapping(value = "/updateUserBalance", method = RequestMethod.POST)
    @ApiOperation(value = "修改人员余额", httpMethod = "POST")
    public ResponseData<Integer> updateUserBalance(@RequestBody UpdateUserBalanceDto updateUserBalanceDto) {
        Integer result = cpuCardService.updateUserBalance(updateUserBalanceDto);
        return ResponseData.OK(result);
    }


    @RequestMapping(value = "/recharge", method = RequestMethod.POST)
    @ApiOperation(value = "卡片余额充值接口", httpMethod = "POST")
    @SysUserLogin
    public ResponseData<Boolean> recharge(@RequestBody RechargeCardDto rechargeCardDto) {
        Boolean rechargeResult = cpuCardService.rechargeSingle(rechargeCardDto);
        return ResponseData.OK(rechargeResult);
    }

    @RequestMapping(value = "/recharge/batch/fileUpload", method = RequestMethod.POST)
    @ApiOperation(value = "批量充值Excel文件上传接口", httpMethod = "POST")
    @SysUserLogin
    public ResponseData<RespRechargeBatchDataDto> fileUpload(@RequestParam("file") MultipartFile file) {
        RespRechargeBatchDataDto respRechargeBatchDataDto = cpuCardService.fileUpload(file);
        return ResponseData.OK(respRechargeBatchDataDto);
    }

    @RequestMapping(value = "/recharge/batch", method = RequestMethod.POST)
    @ApiOperation(value = "批量充值确认接口", httpMethod = "POST")
    @SysUserLogin
    public ResponseData<Boolean> rechargeBatch(@RequestParam String fileCode) {
        Boolean rechargeResult = cpuCardService.rechargeBatch(fileCode);
        return ResponseData.OK(rechargeResult);
    }

    @RequestMapping(value = "/returnCard", method = RequestMethod.POST)
    @ApiOperation(value = "退卡", httpMethod = "POST")
    @SysUserLogin
    public ResponseData<Boolean> rechargeBatch(@RequestBody QueryCardInfoDto queryCardInfoDto) {
        Boolean result = returnCardService.returnCard(queryCardInfoDto.getCardId());
        return ResponseData.OK(result);
    }

    @RequestMapping(value = "/amount", method = RequestMethod.POST)
    @ApiOperation(value = "查询卡片总金额接口", httpMethod = "POST")
    @SysUserLogin
    public ResponseData<RespQueryCardInfoDto> queryAmount(@RequestBody QueryCardInfoDto queryCardInfoDto) {
        queryCardInfoDto.setCardId(StringTools.cardDecimalToHexString(queryCardInfoDto.getCardId()));
        return ResponseData.OK(cpuCardService.queryAmount(queryCardInfoDto));
    }


    @RequestMapping(value = "/making/batch/fileUpload", method = RequestMethod.POST)
    @ApiOperation(value = "批量制卡Excel文件上传接口", httpMethod = "POST")
    @SysUserLogin
    public ResponseData<BatchMarkingCardRespDto> batchMakingCard(@RequestParam("file") MultipartFile file) {
        BatchMarkingCardRespDto batchMarkingCardRespDto = cpuCardService.batchMakingCard(file);
        return ResponseData.OK(batchMarkingCardRespDto);
    }

    @RequestMapping(value = "/making/startStop", method = RequestMethod.POST)
    @ApiOperation(value = "CPU卡启停接口", httpMethod = "POST")
    @SysUserLogin
    public ResponseData<BatchMarkingCardRespDto> batchMakingCard(@RequestBody StartStopDto startStopDto) {
        boolean bool = cpuCardService.startStop(startStopDto.getIfUsed(), startStopDto.getCardId());
        return ResponseData.OK(bool);
    }



    @RequestMapping(value = "/getCpuCardAndUserInfo", method = RequestMethod.POST)
    @ApiOperation(value = "查询卡片信息和人员信息", httpMethod = "POST")
    public ResponseData<List<RespCpuCardAndUserInfoDto>> getCpuCardAndUserInfo(@RequestBody SerianNoDto serianNoDto) {
        RespCpuCardAndUserInfoDto dto=cpuCardService.getCpuCardAndUserInfo(serianNoDto);
        List<RespCpuCardAndUserInfoDto> list=new ArrayList<>();
        list.add(dto);
        return ResponseData.OK(list);
    }

    @RequestMapping(value = "/del", method = RequestMethod.POST)
    @ApiOperation(value = "删除卡片信息和人员信息", httpMethod = "POST")
    public ResponseData<Integer> del(@RequestBody GetUserDto getUserDto) {
       Integer result=cpuCardService.del(getUserDto);
        return ResponseData.OK(result);
    }


}