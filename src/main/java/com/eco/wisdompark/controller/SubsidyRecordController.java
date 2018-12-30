package com.eco.wisdompark.controller;


import com.eco.wisdompark.common.dto.ResponseData;
import com.eco.wisdompark.domain.dto.req.consumeRecord.SearchConsumeRecordDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * CPU卡-补助记录表 前端控制器
 * </p>
 *
 * @author litao
 * @since 2018-12-28
 */
@RestController
@RequestMapping("api/subsidy-record")
@Api(value = "补助记录API", description = "补助记录API")
public class SubsidyRecordController {

    @RequestMapping(value = "/searchUserSubsidyRecordDtos", method = RequestMethod.POST)
    @ApiOperation(value = "查询人员补助记录", httpMethod = "POST")
    public ResponseData searchUserSubsidyRecordDtos(SearchConsumeRecordDto searchConsumeRecordDto) {
        return ResponseData.OK();
    }

}
