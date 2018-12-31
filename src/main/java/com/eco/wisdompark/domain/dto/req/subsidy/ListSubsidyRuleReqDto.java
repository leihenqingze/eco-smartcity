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
@ApiModel(value = "自动补助规则分页列表查询对象", description = "自动补助-补助规则")
public class ListSubsidyRuleReqDto {

    @NotNull(message = "组织架构ID不能为空")
    @ApiModelProperty(value = "组织架构ID")
    private Integer deptId;

}