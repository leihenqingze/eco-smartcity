package com.eco.wisdompark.domain.dto.req.card;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="查询卡片信息", description="查询卡片信息")
public class QueryCardInfoDto {

    @ApiModelProperty(value = "CUP卡物理Id")
    private String cardId;
}
