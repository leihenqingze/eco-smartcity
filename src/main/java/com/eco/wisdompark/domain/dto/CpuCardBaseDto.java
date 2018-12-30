package com.eco.wisdompark.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="CPU卡基础信息Dto", description="CPU卡基础信息Dto")
public class CpuCardBaseDto {

    @ApiModelProperty(value = "卡编号")
    private Integer id;

    @ApiModelProperty(value = "人员姓名")
    private String userName;

    @ApiModelProperty(value = "人员手机号")
    private String phoneNum;

    @ApiModelProperty(value = "人员身份证号")
    private String userCardNum;

    @ApiModelProperty(value = "组织架构Id")
    private Integer deptId;

}
