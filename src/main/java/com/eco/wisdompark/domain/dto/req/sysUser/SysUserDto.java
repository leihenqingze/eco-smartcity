package com.eco.wisdompark.domain.dto.req.sysUser;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="查询系统人员", description="查询系统人员")
public class SysUserDto {

    @ApiModelProperty(value = "系统用户Id")
    private Integer id;

    @ApiModelProperty(value = "系统用户名称")
    private String sysUserName;

    @ApiModelProperty(value = "系统用户手机号")
    private String sysUserPhone;

    @ApiModelProperty(value = "系统用户所属部门（0.膳食处，1.财务处，2.保卫处等")
    private Integer sysUserDepartment;

    @ApiModelProperty(value = "当前页")
    private  Integer currentPage;

    @ApiModelProperty(value = "每页长度")
    private Integer pageSize;
}
