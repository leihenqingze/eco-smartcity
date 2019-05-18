package com.eco.wisdompark.domain.dto.req.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="发送短信", description="发送短信")
public class SmsDto {

    @ApiModelProperty(value = "人员手机号")
    private String phoneNum;
}
