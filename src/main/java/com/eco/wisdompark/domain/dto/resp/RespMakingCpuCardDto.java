package com.eco.wisdompark.domain.dto.resp;

import com.eco.wisdompark.domain.dto.CpuCardBaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.math.BigDecimal;

@Data
@ApiModel(value="制卡返回Dto", description="制卡返回Dto")
public class RespMakingCpuCardDto extends CpuCardBaseDto {

    @ApiModelProperty(value = "CUP卡押金")
    private BigDecimal deposit;

}
