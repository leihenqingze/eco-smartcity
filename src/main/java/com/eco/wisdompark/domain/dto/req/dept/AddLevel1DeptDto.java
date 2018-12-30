package com.eco.wisdompark.domain.dto.req.dept;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="添加组织架构一级", description="添加组织架构一级")
public class AddLevel1DeptDto {

    @ApiModelProperty(value = "组织架构名称")
    private String deptName;

    @ApiModelProperty(value = "消费身份：1训练局职工，2非训练局职工，3保安，4保洁")
    private Boolean consumeIdentity;




}
