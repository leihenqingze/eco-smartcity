package com.eco.wisdompark.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.eco.wisdompark.common.dto.ResponseData;
import com.eco.wisdompark.domain.dto.req.bus.BusRecordDto;
import com.eco.wisdompark.domain.dto.req.bus.SearchBusRecordDto;
import com.eco.wisdompark.domain.dto.req.consumeRecord.AppConsumeRecordDto;
import com.eco.wisdompark.service.BusRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
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

    @RequestMapping(value = "/app_list", method = RequestMethod.POST)
    @ApiOperation(value = "App端查询个人乘车记录", httpMethod = "POST")
    public ResponseData<List<BusRecordDto>> list(@RequestBody AppConsumeRecordDto appConsumeRecordDto) {

        if(StringUtils.isEmpty(appConsumeRecordDto.getCardId())){
            return ResponseData.ERROR(ResponseData.STATUS_CODE_609,"未绑卡!");
        }

        return ResponseData.OK(busRecordService.getBusRecordByCardId(appConsumeRecordDto.getCardId()));
    }
}
