package com.eco.wisdompark.domain.dto.req.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.sf.oval.constraint.Length;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;

@Data
@ApiModel(value = "人员登录", description = "人员登录")
public class UserLoginDto {

    @ApiModelProperty(value = "人员手机号")
    @NotNull(message = "人员手机号不能为空")
    @Length(min = 11,max = 11,message = "人员手机号长度只能为11位")
    private String phoneNum;

    @NotNull(message = "手机验证码不能为空")
    @NotEmpty(message = "手机验证码不能为空")
    @ApiModelProperty(value = "手机验证码")
    private String captcha;

}