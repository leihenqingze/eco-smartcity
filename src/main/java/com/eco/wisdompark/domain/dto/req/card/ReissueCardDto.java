package com.eco.wisdompark.domain.dto.req.card;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.sf.oval.constraint.NotNull;

import java.math.BigDecimal;

@Data
@ApiModel(value="卡片挂失补发", description="卡片挂失补发")
public class ReissueCardDto {

    @ApiModelProperty(value = "用户Id")
    @NotNull(message = "用户Id不能为空")
    private Integer userId;

    @ApiModelProperty(value = "CPU卡物理ID")
    @NotNull(message = "未能读取到CPU卡物理ID，请重试")
    private String cardId;

    @ApiModelProperty(value = "CPU卡面序列号")
    private String cardSerialNo;

    @ApiModelProperty(value = "CPU卡押金")
    private BigDecimal deposit;
}
