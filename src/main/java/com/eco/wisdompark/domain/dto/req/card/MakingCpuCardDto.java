package com.eco.wisdompark.domain.dto.req.card;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel(value="制卡", description="制卡")
public class MakingCpuCardDto {

    @ApiModelProperty(value = "CUP卡物理Id")
    private String cardId;

    @ApiModelProperty(value = "CUP卡面印刷的序列号")
    @TableField("card_serialNo")
    private String cardSerialno;

    @ApiModelProperty(value = "CUP卡押金")
    private BigDecimal deposit;

    @ApiModelProperty(value = "用户Id")
    private Integer userId;

    @ApiModelProperty(value = "CUP卡类型，1ID，2IC，3CPU,4虚拟卡")
    private Integer cardType;

    @ApiModelProperty(value = "CUP卡来源，1制卡，2激活")
    private Integer cardSource;

    @ApiModelProperty(value = "CUP卡充值余额")
    private BigDecimal rechargeBalance;

    @ApiModelProperty(value = "CUP卡补助余额")
    private BigDecimal subsidyBalance;

}
