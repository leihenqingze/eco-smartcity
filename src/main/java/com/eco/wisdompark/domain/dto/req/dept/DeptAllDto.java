package com.eco.wisdompark.domain.dto.req.dept;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class DeptAllDto {


    @ApiModelProperty(value = "组织架构ID")
    private Integer value;
    @ApiModelProperty(value = "组织架构名称")
    private  String label;
    @ApiModelProperty(value = "二级组织架构集合")
    private List<DeptAllDto> children;


}
