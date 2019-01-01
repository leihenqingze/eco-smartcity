package com.eco.wisdompark.domain.dto.req.subsidy;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import net.sf.oval.constraint.NotNull;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "自动补助记录查询", description = "自动补助记录查询")
public class SearchAutoSubsidyRecordReq {

    @NotNull(message = "补助规则ID不能为空")
    @ApiModelProperty(value = "补助规则ID")
    private Integer ruleId;

    @NotNull(message = "补助时间不能为空")
    @ApiModelProperty(value = "补助时间")
    private LocalDate subsidyTime;

}