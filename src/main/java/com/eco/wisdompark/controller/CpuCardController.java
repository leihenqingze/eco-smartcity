package com.eco.wisdompark.controller;


import com.eco.wisdompark.common.dto.ResponseData;
import com.eco.wisdompark.domain.dto.req.card.CardRechargeDto;
import com.eco.wisdompark.domain.dto.req.card.MakingCpuCardDto;
import com.eco.wisdompark.domain.dto.req.card.QueryCardInfoDto;
import com.eco.wisdompark.domain.dto.req.consumeRecord.SearchConsumeRecordDto;
import io.swagger.annotations.ApiOperation;
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
public class CpuCardController {

    @RequestMapping(value = "/making", method = RequestMethod.POST)
    @ApiOperation(value = "制卡/卡片激活接口", httpMethod = "POST")
    public ResponseData makingCpuCardDtos(@RequestBody MakingCpuCardDto makingCpuCardDto) {
        return ResponseData.OK();
    }

    @RequestMapping(value = "/query", method = RequestMethod.POST)
    @ApiOperation(value = "查询卡片信息接口", httpMethod = "POST")
    public ResponseData queryCardInfo(@RequestBody QueryCardInfoDto queryCardInfoDto) {
        return ResponseData.OK();
    }

    @RequestMapping(value = "/recharge", method = RequestMethod.POST)
    @ApiOperation(value = "卡片余额充值接口", httpMethod = "POST")
    public ResponseData cardRecharge(@RequestBody CardRechargeDto cardRechargeDto) {
        return ResponseData.OK();
    }

    @RequestMapping(value = "/recharge/batch", method = RequestMethod.POST)
    @ApiOperation(value = "批量充值接口", httpMethod = "POST")
    public ResponseData batchRecharge(@RequestParam("file") MultipartFile file) {
        String fileName = file.getOriginalFilename();
//        try {
//            a = testService.batchImport(fileName, file);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return ResponseData.OK();
    }




}
