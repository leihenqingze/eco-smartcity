package com.eco.wisdompark.domain.dto.resp;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.eco.wisdompark.domain.dto.req.rechargeRecord.RechargeRecordDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@ApiModel(value="CPU卡充值记录返回Dto", description="CPU卡充值记录返回Dto")
public class RespRechargeRecordDataDto {

    @ApiModelProperty(value = "分页信息")
    private IPage<RechargeRecordDto> rechargeRecordDtoPage;

    @ApiModelProperty(value = "总金额")
    private BigDecimal totalAmount = BigDecimal.ZERO;

}
