package com.eco.wisdompark.domain.dto.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "自动补助规则分页列表", description = "自动补助-补助规则")
public class ListSubsidyRuleRespDto {

    @ApiModelProperty(value = "补助规则Id")
    private Integer id;

    @ApiModelProperty(value = "组织架构名称")
    private String deptName;

    @ApiModelProperty(value = "补助时间")
    private Integer subsidyTime;

    @ApiModelProperty(value = "补助金额")
    private BigDecimal subsidyAmount;

    @ApiModelProperty(value = "补助状态（是否停止补助）")
    private Integer subsidyStatus;

}