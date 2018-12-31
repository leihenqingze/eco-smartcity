package com.eco.wisdompark.domain.dto.resp;

import com.eco.wisdompark.domain.dto.CpuCardBaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel(value="CPU卡挂失补卡信息Dto", description="CPU卡挂失补卡信息Dto")
public class RespReissueCardDto extends CpuCardBaseDto {

    @ApiModelProperty(value = "老卡物理ID")
    private String oldCardId;

    @ApiModelProperty(value = "新卡物理ID")
    private String newCardId;

    @ApiModelProperty(value = "余额")
    private BigDecimal balance;

}
