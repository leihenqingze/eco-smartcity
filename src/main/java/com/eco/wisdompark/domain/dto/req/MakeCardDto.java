package com.eco.wisdompark.domain.dto.req;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel(value="制卡", description="制卡")
public class MakeCardDto {

    @ApiModelProperty(value = "CUP卡物理Id")
    private String cardId;

    @ApiModelProperty(value = "CUP卡面印刷的序列号")
    @TableField("card_serialNo")
    private String cardSerialno;

    @ApiModelProperty(value = "CUP卡押金")
    private BigDecimal deposit;

}