package com.eco.wisdompark.domain.dto.req.dept;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GetLevel2DeptDto {

    @ApiModelProperty(value = "组织架构名称")
    private String deptName;

    @ApiModelProperty(value = "消费身份")
    private Integer consumeIdentity;
}
