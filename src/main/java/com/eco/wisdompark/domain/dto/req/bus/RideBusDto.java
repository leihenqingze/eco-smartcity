package com.eco.wisdompark.domain.dto.req.bus;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.sf.oval.constraint.Min;
import net.sf.oval.constraint.NotEmpty;

import java.math.BigDecimal;

@Data
@ApiModel(value="刷卡乘车请求体", description="刷卡乘车请求体")
public class RideBusDto {

    @NotEmpty(message = "卡物理Id不能为空")
    @ApiModelProperty(value = "卡物理Id")
    private String cardId;

    @NotEmpty(message = "班车Id不能为空")
    @ApiModelProperty(value = "班车ID")
    private Integer busId;

}
