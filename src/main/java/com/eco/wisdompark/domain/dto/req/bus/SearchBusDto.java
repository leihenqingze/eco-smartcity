package com.eco.wisdompark.domain.dto.req.bus;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="查询班车请求体", description="查询班车请求体")
public class SearchBusDto {

    @ApiModelProperty(value = "班车车牌号")
    private String busNum;
}
