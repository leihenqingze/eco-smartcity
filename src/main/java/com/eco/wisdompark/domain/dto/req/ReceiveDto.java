package com.eco.wisdompark.domain.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "接收捷顺推送数据的请求体", description = "接收捷顺推送数据的请求体")
public class ReceiveDto {

    @ApiModelProperty(value = "返回码")
    private String resultCode;

    @ApiModelProperty(value = "返回信息")
    private String message;

    @ApiModelProperty(value = "返回结果集")
    private String dataItems;

    @ApiModelProperty(value = "记录唯一标识")
    private String itemId;

    @ApiModelProperty(value = "业务数据返回码")
    private String code;


}
