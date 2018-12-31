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
@ApiModel(value = "修改自动补助规则", description = "自动补助-补助规则")
public class UpdateSubsidyRuleDto {

    @NotNull(message = "补助规则Id不能为空")
    @ApiModelProperty(value = "补助规则Id")
    private Integer id;

    @NotNull(message = "补助金额不能为空")
    @Min(value = 0, message = "补助金额不能小于0")
    @Max(value = Double.MAX_VALUE, message = "补助金额超出限制")
    @ApiModelProperty(value = "补助金额")
    private BigDecimal subsidyAmount;

}