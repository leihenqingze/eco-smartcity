package com.eco.wisdompark.domain.dto.req.sysUser;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.sf.oval.constraint.NotNull;


@Data
@ApiModel(value="修改个人密码", description="修改个人密码")
public class UpdateUserPassDto {

    @ApiModelProperty(value = "旧密码")
    @NotNull
    private String oldPassWord;

    @ApiModelProperty(value = "新密码")
    @NotNull
    private String newPassWord;

    @ApiModelProperty(value = "确认新密码")
    @NotNull
    private String confirmNewPassWord;
}
