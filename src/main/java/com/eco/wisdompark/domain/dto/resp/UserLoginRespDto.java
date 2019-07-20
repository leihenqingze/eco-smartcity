package com.eco.wisdompark.domain.dto.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "人员登录返回信息", description = "人员登录返回信息")
public class UserLoginRespDto {

    @ApiModelProperty(value = "人员姓名")
    private String userName;
    @ApiModelProperty(value = "CUP卡物理Id, -1:表示未绑卡")
    private String cardId;
    @ApiModelProperty(value = "CUP卡物理Id十六进制, -1:表示未绑卡")
    private String cardIdHex;
    @ApiModelProperty(value = "CUP卡物理Id十六进制, -1:表示未绑卡")
    private Integer userId;

}