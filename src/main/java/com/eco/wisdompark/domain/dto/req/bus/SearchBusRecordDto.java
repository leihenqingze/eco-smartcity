package com.eco.wisdompark.domain.dto.req.bus;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="查询乘车记录请求体", description="查询乘车记录请求体")
public class SearchBusRecordDto {

    @ApiModelProperty(value = "班车id")
    private Integer busId;

    @ApiModelProperty(value = "卡id")
    private String cardId;

    @ApiModelProperty(value = "当前页")
    private  Integer currentPage=0;

    @ApiModelProperty(value = "每页长度")
    private Integer pageSize=10;

}
