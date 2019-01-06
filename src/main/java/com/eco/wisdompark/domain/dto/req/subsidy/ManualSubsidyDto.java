package com.eco.wisdompark.domain.dto.req.subsidy;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import net.sf.oval.constraint.Max;
import net.sf.oval.constraint.Min;
import net.sf.oval.constraint.NotNull;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "手动补助请求对象", description = "手动补助")
public class ManualSubsidyDto {

    @ApiModelProperty(value = "CPU卡ID")
    @NotNull(message = "CPU卡ID为空")
    private Integer cpuCardId;

    @NotNull(message = "补助金额不能为空")
    @Min(value = 0, message = "补助金额不能小于0")
    @Max(value = Double.MAX_VALUE, message = "补助金额超出限制")
    @ApiModelProperty(value = "补助金额")
    private BigDecimal subsidyAmount;

}