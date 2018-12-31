package com.eco.wisdompark.domain.dto.req.subsidy;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import net.sf.oval.constraint.NotNull;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "启停自动补助规则", description = "自动补助-补助规则")
public class RevStopSubsidyRuleDto {

    @NotNull(message = "补助规则Id不能为空")
    @ApiModelProperty(value = "补助规则Id")
    private Integer id;

    @NotNull(message = "补助状态不能为空")
    @ApiModelProperty(value = "补助状态（是否停止补助）")
    private Integer subsidyStatus;

}