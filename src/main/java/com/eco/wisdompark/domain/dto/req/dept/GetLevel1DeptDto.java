package com.eco.wisdompark.domain.dto.req.dept;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="组织架构", description="组织架构")
public class GetLevel1DeptDto {

    @ApiModelProperty(value = "组织架构名称")
    private String deptName;

    @ApiModelProperty(value = "消费身份")
    private Integer consumeIdentity;
}
