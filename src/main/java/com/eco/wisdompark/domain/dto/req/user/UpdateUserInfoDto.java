package com.eco.wisdompark.domain.dto.req.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.sf.oval.constraint.Length;
import net.sf.oval.constraint.NotNull;

@Data
@ApiModel(value="修改人员信息", description="修改人员信息请求体")
public class UpdateUserInfoDto {

    @ApiModelProperty(value = "人员ID")
    @NotNull( message = "人员Id不能空")
    private Integer id;

    @ApiModelProperty(value = "人员姓名")
    @NotNull(message = "人员姓名不能为空")
    private String userName;

    @ApiModelProperty(value = "人员手机号")
    @NotNull(message = "人员手机号不能为空")
    @Length(min = 11,max = 11,message = "人员手机号长度只能为11位")
    private String phoneNum;

    @ApiModelProperty(value = "人员身份证号")
    @NotNull(message = "人员身份证号不能为空")
    private String userCardNum;
}
