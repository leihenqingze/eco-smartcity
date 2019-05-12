package com.eco.wisdompark.domain.dto.req.consumeRecord;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="App端消费记录", description="App端消费记录")
public class AppConsumeRecordDto {

    @ApiModelProperty(value = "卡id")
    private String cardId;

}
