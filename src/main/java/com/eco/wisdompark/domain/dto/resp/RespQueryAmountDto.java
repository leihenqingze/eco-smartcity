package com.eco.wisdompark.domain.dto.resp;

import com.eco.wisdompark.domain.dto.CpuCardBaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel(value = "查询总金额返回信息", description = "查询总金额返回信息")
public class RespQueryAmountDto extends CpuCardBaseDto {

    @ApiModelProperty(value = "CUP卡总余额")
    private BigDecimal totalBalance;

}
