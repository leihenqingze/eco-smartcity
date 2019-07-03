package com.eco.wisdompark.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.eco.wisdompark.common.aop.SysUserLogin;
import com.eco.wisdompark.common.dto.ResponseData;
import com.eco.wisdompark.domain.dto.req.rechargeRecord.RechargeRecordDto;
import com.eco.wisdompark.domain.dto.req.rechargeRecord.SearchRechargeRecordDto;
import com.eco.wisdompark.domain.dto.resp.RespRechargeRecordDataDto;
import com.eco.wisdompark.service.RechargeRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * CPU卡-充值记录表 前端控制器
 * </p>
 *
 * @author litao
 * @since 2018-12-28
 */
@RestController
@RequestMapping("api/recharge-record")
@Api(value = "充值记录API", description = "充值记录API")
public class RechargeRecordController {
    @Autowired
    private RechargeRecordService rechargeRecordService;


    @RequestMapping(value = "/searchUserRechargeRecordDtos", method = RequestMethod.POST)
    @ApiOperation(value = "查询人员充值记录", httpMethod = "POST")
    @SysUserLogin
    public ResponseData<RespRechargeRecordDataDto> searchUserRechargeRecordDtos( @RequestBody SearchRechargeRecordDto searchRechargeRecordDto) {
        RespRechargeRecordDataDto respRechargeRecordDataDto = new RespRechargeRecordDataDto();
        IPage<RechargeRecordDto> rechargePage = rechargeRecordService.searchUserRechargeRecordDtos(searchRechargeRecordDto);
        respRechargeRecordDataDto.setRechargeRecordDtoPage(rechargePage);
        respRechargeRecordDataDto.setTotalAmount(rechargeRecordService.totalRechargeAmount(searchRechargeRecordDto));
        return ResponseData.OK(respRechargeRecordDataDto);
    }

    @RequestMapping(value = "/exportUserRechargeRecordDtos", method = RequestMethod.POST)
    @ApiOperation(value = "导出人员充值记录", httpMethod = "POST")
    @SysUserLogin
    public void exportShopPosConsumeRecordDtos( @RequestBody SearchRechargeRecordDto searchRechargeRecordDto,
                                               HttpServletResponse response) {
        rechargeRecordService.exportShopPosConsumeRecordDtos(searchRechargeRecordDto,response);
    }

}
