package com.eco.wisdompark.domain.dto.req.consumeRecord;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value="训练局职工消费记录", description="训练局职工消费记录")
public class TrainingStaffConsumeRecordDto {


    @ApiModelProperty(value = "二级部门id")
    private Integer deptId;

    @ApiModelProperty(value = "pos机位置id,0东职,1西职，2中心，3购物")
    private Integer posPositionId;

    @ApiModelProperty(value = "起始时间")
    private String startTime;

    @ApiModelProperty(value = "结束时间")
    private String endTime;

    @ApiModelProperty(value = "当前页")
    private  Integer currentPage=0;

    @ApiModelProperty(value = "每页长度")
    private Integer pageSize=10;



}
