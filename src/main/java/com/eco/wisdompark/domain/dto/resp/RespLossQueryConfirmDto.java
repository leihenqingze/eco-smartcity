package com.eco.wisdompark.domain.dto.resp;

import com.eco.wisdompark.domain.dto.CpuCardBaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel(value="CPU卡挂失补卡确认信息Dto", description="CPU卡挂失补卡确认信息Dto")
public class RespLossQueryConfirmDto extends CpuCardBaseDto {

    @ApiModelProperty(value = "老卡物理ID")
    private String oldCardId;

    @ApiModelProperty(value = "新卡物理ID")
    private String newCardId;

    @ApiModelProperty(value = "CUP卡充值余额")
    private BigDecimal rechargeBalance;

    @ApiModelProperty(value = "CUP卡补助余额")
    private BigDecimal subsidyBalance;

    /**
     * 总的余额（充值余额 + 补助余额）
     */
    @ApiModelProperty(value = "余额")
    private BigDecimal balance;



}
