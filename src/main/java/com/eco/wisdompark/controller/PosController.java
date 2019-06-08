package com.eco.wisdompark.controller;

import com.eco.wisdompark.common.aop.SysUserLogin;
import com.eco.wisdompark.common.dto.ResponseData;
import com.eco.wisdompark.domain.dto.req.card.QueryCardInfoDto;
import com.eco.wisdompark.domain.dto.req.consume.ConsumeDto;
import com.eco.wisdompark.domain.dto.resp.ConsumeRespDto;
import com.eco.wisdompark.domain.dto.resp.ConsumeServiceRespDto;
import com.eco.wisdompark.domain.dto.resp.RespQueryCardInfoDto;
import com.eco.wisdompark.service.ConsumeService;
import com.eco.wisdompark.service.CpuCardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * <p>
 * pos机 前端控制器
 * </p>
 *
 * @author litao
 * @since 2018-12-28
 */
@Api(value = "POS机配置API", description = "POS机配置API")
@RestController
@RequestMapping("/api/pos")
@Slf4j
public class PosController {

    @Autowired
    private ConsumeService consumeService;

    @Autowired
    private CpuCardService cpuCardService;

    @RequestMapping(value = "/consume", method = RequestMethod.POST)
    @ApiOperation(value = "刷卡消费", httpMethod = "POST")
    public ResponseData<ConsumeRespDto> consume(@RequestBody ConsumeDto consumeDto) {
        log.error(">>>>>>>>>>pos num {}",consumeDto.getPosNum());
        log.error(">>>>>>>>>>cardId  {}",consumeDto.getCardId());
        ConsumeServiceRespDto consumeServiceRespDto = consumeService.consume(consumeDto);
        if (Objects.isNull(consumeServiceRespDto.getErrorCode())) {
            return ResponseData.OK(consumeServiceRespDto);
        } else {
            ResponseData<ConsumeRespDto> result =
                    ResponseData.ERROR(consumeServiceRespDto.getErrorCode(), consumeServiceRespDto.getNextConsume());
            result.setData(consumeServiceRespDto);
            return result;
        }
    }

    @RequestMapping(value = "/query", method = RequestMethod.POST)
    @ApiOperation(value = "查询卡片信息接口", httpMethod = "POST")
    @SysUserLogin
    public ResponseData<RespQueryCardInfoDto> queryCardInfo(@RequestBody QueryCardInfoDto queryCardInfoDto) {
        RespQueryCardInfoDto respQueryCardInfoDto = cpuCardService.queryCardInfo(queryCardInfoDto);
        return ResponseData.OK(respQueryCardInfoDto);
    }

}