package com.eco.wisdompark.domain.dto.resp;

import com.eco.wisdompark.domain.dto.CpuCardBaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel(value = "查询CPU卡信息返回Dto", description = "查询CPU卡信息返回Dto")
public class RespQueryCardInfoDto extends CpuCardBaseDto {

    @ApiModelProperty(value = "CUP卡充值余额")
    private BigDecimal rechargeBalance;

    @ApiModelProperty(value = "CUP卡补助余额")
    private BigDecimal subsidyBalance;

    @ApiModelProperty(value = "CUP卡总余额")
    private BigDecimal totalBalance;

    @ApiModelProperty(value = "押金")
    private BigDecimal deposit;

    @ApiModelProperty(value = "CUP卡挂失状态：0在用，1挂失，2退卡")
    private Integer reportLossStstus;

}
