package com.eco.wisdompark.controller;


import com.eco.wisdompark.common.dto.ResponseData;
import com.eco.wisdompark.domain.dto.resp.ConsomeRecordRespDto;
import com.eco.wisdompark.service.ConsumeRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * CPU卡-消费记录表 前端控制器
 * </p>
 *
 * @author litao
 * @since 2018-12-28
 */
@RestController
@RequestMapping("api/consume-record")
@Api(value = "消费记录API", description = "消费记录API")
public class ConsumeRecordController {

    @Autowired
    private ConsumeRecordService consumeRecordService;


    @RequestMapping(value = "/trainingStaffRecord", method = RequestMethod.GET)
    @ApiOperation(value = "训练局职工消费记录", httpMethod = "GET")
    public ResponseData<ConsomeRecordRespDto> trainingStaffRecord(@Param("deptId") Integer deptId,
                                                                  @Param("posPositionId") Integer posPositionId,
                                                                  @Param("startTime") String startTime,
                                                                  @Param("endTime") String endTime) {
        return ResponseData.OK();
    }

    @RequestMapping(value = "/notTrainingStaffRecord", method = RequestMethod.GET)
    @ApiOperation(value = "非训练局职工消费记录", httpMethod = "GET")
    public ResponseData<ConsomeRecordRespDto> notTrainingStaffRecord(@Param("deptId") Integer deptId,
                                                                  @Param("consomeType") Integer consomeType,
                                                                  @Param("startTime") String startTime,
                                                                  @Param("endTime") String endTime) {
        return ResponseData.OK();
    }

    @RequestMapping(value = "/securityRecord", method = RequestMethod.GET)
    @ApiOperation(value = "保安消费记录", httpMethod = "GET")
    public ResponseData<ConsomeRecordRespDto> securityRecord(@Param("startTime") String startTime,
                                                             @Param("endTime") String endTime) {
        return ResponseData.OK();
    }

    @RequestMapping(value = "/cleaningRecord", method = RequestMethod.GET)
    @ApiOperation(value = "保洁消费记录", httpMethod = "GET")
    public ResponseData<ConsomeRecordRespDto> cleaningRecord(@Param("startTime") String startTime,
                                                             @Param("endTime") String endTime) {
        return ResponseData.OK();
    }

}
