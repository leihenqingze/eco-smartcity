package com.eco.wisdompark.domain.dto.req.subsidyRecord;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="查询补助记录", description="查询补助记录")
public class SearchSubsidyRecordDto {

    @ApiModelProperty(value = "卡片编号")
    private String card_serialNo;

    @ApiModelProperty(value = "人员姓名")
    private String userName;

    @ApiModelProperty(value = "部门ID")
    private Integer deptId;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "起始时间")
    private String startTime;

    @ApiModelProperty(value = "结束时间")
    private String endTime;

    @ApiModelProperty(value = "当前页")
    private  Integer currentPage=0;

    @ApiModelProperty(value = "每页长度")
    private Integer pageSize=20;

}
