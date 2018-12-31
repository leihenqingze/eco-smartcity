package com.eco.wisdompark.domain.dto.req.consume;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.sf.oval.constraint.Min;
import net.sf.oval.constraint.NotNull;

import java.math.BigDecimal;

@Data
public class ConsumeDto {

    @NotNull(message = "卡物理Id不能为空")
    @ApiModelProperty(value = "卡物理Id")
    private String cardId;

    @NotNull(message = "设备编号不能为空")
    @ApiModelProperty(value = "设备编号ID")
    private String posNum;

    @NotNull(message = "消费金额不能为空")
    @Min(value = 0, message = "消费金额不能小于0")
    @ApiModelProperty(value = "消费金额")
    private BigDecimal amount;

}
