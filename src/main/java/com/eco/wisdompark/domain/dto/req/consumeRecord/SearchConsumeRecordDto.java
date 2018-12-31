package com.eco.wisdompark.domain.dto.req.consumeRecord;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NonNull;
import net.sf.oval.constraint.NotNull;

@Data
@ApiModel(value="查询消费记录", description="查询消费记录")
public class SearchConsumeRecordDto {


    @ApiModelProperty(value = "人员ID")
    @NotNull( message = "人员Id不能空")
    private Integer id;

    @ApiModelProperty(value = "起始时间")
    private String startTime;

    @ApiModelProperty(value = "结束时间")
    private String endTime;

    @ApiModelProperty(value = "当前页")
    private  Integer currentPage=0;

    @ApiModelProperty(value = "每页长度")
    private Integer pageSize=20;



}
