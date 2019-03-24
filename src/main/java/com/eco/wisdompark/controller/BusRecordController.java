package com.eco.wisdompark.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.eco.wisdompark.common.dto.ResponseData;
import com.eco.wisdompark.domain.dto.req.bus.BusRecordDto;
import com.eco.wisdompark.domain.dto.req.bus.SearchBusRecordDto;
import com.eco.wisdompark.domain.dto.req.consumeRecord.FinanceConsumeRecordDto;
import com.eco.wisdompark.domain.dto.req.consumeRecord.TrainingStaffConsumeRecordDto;
import com.eco.wisdompark.domain.dto.resp.ConsomeRecordRespDto;
import com.eco.wisdompark.domain.model.SysUser;
import com.eco.wisdompark.enums.ConsumeIdentity;
import com.eco.wisdompark.service.BusRecordService;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 班车乘车记录 前端控制器
 * </p>
 *
 * @author zhangkai
 * @since 2019-03-24
 */
@RestController
@RequestMapping("api/bus-record")
@Api(value = "班车乘车记录API", description = "班车乘车记录API")
public class BusRecordController {

    @Autowired
    BusRecordService busRecordService;

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ApiOperation(value = "班车乘车记录", httpMethod = "POST")
    public ResponseData<IPage<BusRecordDto>> list(@RequestBody SearchBusRecordDto searchBusRecordDto) {

        IPage<BusRecordDto> busRecordDtoPage = busRecordService.getBusRecordByQuery(searchBusRecordDto);

        return ResponseData.OK(busRecordDtoPage);
    }
}
