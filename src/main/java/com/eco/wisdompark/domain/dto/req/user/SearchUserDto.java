package com.eco.wisdompark.domain.dto.req.user;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="查询人员列表", description="查询人员列表")
public class SearchUserDto {

    @ApiModelProperty(value = "人员姓名")
    private String userName;

    @ApiModelProperty(value = "人员手机号")
    private String phoneNum;

    @ApiModelProperty(value = "组织架构Id")
    private Integer deptId;


    @ApiModelProperty(value = "当前页")
    private  Integer currentPage;

    @ApiModelProperty(value = "每页长度")
    private Integer pageSize;
}
