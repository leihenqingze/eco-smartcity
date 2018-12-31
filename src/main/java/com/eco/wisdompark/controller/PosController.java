package com.eco.wisdompark.controller;

import com.eco.wisdompark.common.dto.ResponseData;
import com.eco.wisdompark.domain.dto.req.consume.ConsumeDto;
import com.eco.wisdompark.domain.dto.resp.ConsumeRespDto;
import com.eco.wisdompark.service.ConsumeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
public class PosController {

    @Autowired
    private ConsumeService consumeService;

    @RequestMapping(value = "/consume", method = RequestMethod.POST)
    @ApiOperation(value = "刷卡消费", httpMethod = "POST")
    public ResponseData<ConsumeRespDto> consume(@RequestBody ConsumeDto consumeDto) {
        return ResponseData.OK(consumeService.consume(consumeDto));
    }

}