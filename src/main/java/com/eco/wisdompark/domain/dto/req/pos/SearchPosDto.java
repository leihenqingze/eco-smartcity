package com.eco.wisdompark.domain.dto.req.pos;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="查询POS机请求体", description="查询POS机请求体")
public class SearchPosDto {

    @ApiModelProperty(value = "POS机位置:0东职,1西职，2中心，3购物")
    private Integer posPosition;

    @ApiModelProperty(value = "POS机消费类型：0用餐，1购物")
    private Integer posConsumeType;

    @ApiModelProperty(value = "POS机编号")
    private String posNum;
}
