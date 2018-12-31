package com.eco.wisdompark.domain.dto.req.subsidy;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import net.sf.oval.constraint.*;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "自动补助-补助规则", description = "自动补助-补助规则")
public class AddAutoSubsidyRuleDto {

    @ApiModelProperty(value = "组织架构ID")
    @NotNull(message = "组织架构ID不能为空")
    private Integer deptId;

    @NotNull(message = "补助时间不能为空")
    @Max(value = 28, message = "补助时间不能超过28号")
    @ApiModelProperty(value = "补助时间")
    private Integer subsidyTime;

    @NotNull(message = "补助金额不能为空")
    @Min(value = 0, message = "补助金额不能小于0")
    @Max(value = Double.MAX_VALUE, message = "补助金额超出限制")
    @ApiModelProperty(value = "补助金额")
    private BigDecimal subsidyAmount;

}