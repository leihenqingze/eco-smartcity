package com.eco.wisdompark.controller;


import com.eco.wisdompark.common.dto.ResponseData;
import com.eco.wisdompark.domain.dto.req.bus.RideBusDto;
import com.eco.wisdompark.domain.dto.req.bus.SearchBusDto;
import com.eco.wisdompark.domain.dto.req.consume.ConsumeDto;
import com.eco.wisdompark.domain.dto.req.dept.*;
import com.eco.wisdompark.domain.dto.resp.ConsumeRespDto;
import com.eco.wisdompark.domain.dto.resp.ConsumeServiceRespDto;
import com.eco.wisdompark.domain.model.Bus;
import com.eco.wisdompark.domain.model.BusRecord;
import com.eco.wisdompark.service.BusRecordService;
import com.eco.wisdompark.service.BusService;
import com.eco.wisdompark.service.DeptService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

/**
  * 班车相关
  *
  * @author zhangkai
  * @date 2019/3/24 上午11:45
  */
@RestController
@RequestMapping("api/bus")
@Api(value = "班车API", description = "班车相关API")
public class BusController {

    @Autowired
    private BusService busService;


    @RequestMapping(value = "/getAllBus", method = RequestMethod.POST)
    @ApiOperation(value = "查询所有班车", httpMethod = "POST")
    public ResponseData<List<Bus>> getAllBus( @RequestBody SearchBusDto searchBusDto) {
        List<Bus> result = busService.getBusByQuery(searchBusDto);
        return ResponseData.OK(result);
    }

    @RequestMapping(value = "/ride", method = RequestMethod.POST)
    @ApiOperation(value = "刷卡乘车", httpMethod = "POST")
    public ResponseData<List<Bus>> getAllBus( @RequestBody RideBusDto rideBusDto) {
        busService.cardRideBus(rideBusDto);
        return ResponseData.OK();
    }
}
