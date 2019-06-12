package com.eco.wisdompark.domain.dto.req.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel(value = "修改用户余额", description = "修改用户余额")
public class UpdateUserBalanceDto {

    @ApiModelProperty(value = "人员ID")
    private Integer id;

    @ApiModelProperty(value = "CUP卡充值余额")
    private BigDecimal rechargeBalance;

    @ApiModelProperty(value = "CUP卡补助余额")
    private BigDecimal subsidyBalance;

}
