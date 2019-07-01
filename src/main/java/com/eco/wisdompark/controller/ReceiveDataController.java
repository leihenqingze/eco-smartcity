package com.eco.wisdompark.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Api(value = "接收数据", description = "接收捷顺推送的人员信息与卡片信息数据")
@RestController
@RequestMapping("/api/receive")
@Slf4j
public class ReceiveDataController {

    @RequestMapping(value = "/personInfo", method = RequestMethod.POST)
    @ApiOperation(value = "接收人员信息", httpMethod = "POST")
    public void personInfo(String dataItems) {
        log.info("-------> persionInfo {}" + dataItems);
    }

    @RequestMapping(value = "/cardInfo", method = RequestMethod.POST)
    @ApiOperation(value = "接收卡片信息", httpMethod = "POST")
    public void cardInfo(String dataItems) {
        log.info("-------> cardInfo {}" + dataItems);
    }
}
