package com.eco.wisdompark.domain.dto.req.card;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.sf.oval.constraint.NotEmpty;

@Data
@ApiModel(value = "查询卡片信息", description = "查询卡片信息")
public class QueryCardInfoDto {

    @NotEmpty(message = "CUP卡物理Id不能为空")
    @ApiModelProperty(value = "CUP卡物理Id")
    private String cardId;
}
