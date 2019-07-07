package com.eco.wisdompark.domain.dto.req.consumeRecord;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="物业人员消费记录", description="物业人员消费记录")
public class PropertyConsumeRecordDto {

    @ApiModelProperty(value = "起始时间")
    private String startTime;

    @ApiModelProperty(value = "结束时间")
    private String endTime;

    @ApiModelProperty(value = "当前页")
    private  Integer currentPage=0;

    @ApiModelProperty(value = "每页长度")
    private Integer pageSize=10;

    @ApiModelProperty(value = "用餐类型")
    private Integer diningType;



}
