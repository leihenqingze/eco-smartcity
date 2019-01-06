package com.eco.wisdompark.domain.dto.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "消费返回消息体", description = "消费返回消息体")
public class ConsumeServiceRespDto extends ConsumeRespDto {

    @ApiModelProperty(value = "错误码")
    private Integer errorCode;

}