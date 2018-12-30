package com.eco.wisdompark.domain.dto.req.card;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.sf.oval.constraint.Min;
import net.sf.oval.constraint.NotNull;

import java.math.BigDecimal;

@Data
@ApiModel(value="卡片充值", description="卡片充值")
public class CardRechargeDto {

    @ApiModelProperty(value = "CUP卡物理Id")
    @NotNull(message = "CUP卡信息未读取成功")
    private String cardId;

    @ApiModelProperty(value = "CUP卡面印刷的序列号")
    private String cardSerialno;

    @ApiModelProperty(value = "用户Id")
    @NotNull(message = "用户Id不能为空")
    private Integer userId;

    @ApiModelProperty(value = "充值金额")
    @Min(value = 1, message = "充值金额最小不能小于1元")
    private BigDecimal rechargeAmt;

}
