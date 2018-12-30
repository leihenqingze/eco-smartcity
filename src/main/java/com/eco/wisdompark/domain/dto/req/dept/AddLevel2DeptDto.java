package com.eco.wisdompark.domain.dto.req.dept;

import io.swagger.annotations.ApiModelProperty;

public class AddLevel2DeptDto {

    @ApiModelProperty(value = "组织架构一级ID")
    private Integer id;

    @ApiModelProperty(value = "组织架构名称")
    private String deptName;
}
