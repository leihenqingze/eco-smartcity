package com.eco.wisdompark.domain.dto.req.card;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="查询卡片信息", description="查询卡片信息")
public class QueryCardInfoDto {

    @ApiModelProperty(value = "CUP卡物理Id")
    private String cardId;

    @ApiModelProperty(value = "人员姓名")
    private String userName;

    @ApiModelProperty(value = "人员手机号")
    private String phoneNum;

    @ApiModelProperty(value = "组织架构Id")
    private String deptId;
}
