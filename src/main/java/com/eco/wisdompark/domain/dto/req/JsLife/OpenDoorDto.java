package com.eco.wisdompark.domain.dto.req.JsLife;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 开门请求参数
 *
 * @author zhangkai
 * @date 2019年05月19日 17:58
 */
@Data
@ApiModel(value="开门请求", description="开门请求")
public class OpenDoorDto {

    @ApiModelProperty(value = "卡ID")
    private String cardId;

    @ApiModelProperty(value = "门号")
    private String doorId;
}
