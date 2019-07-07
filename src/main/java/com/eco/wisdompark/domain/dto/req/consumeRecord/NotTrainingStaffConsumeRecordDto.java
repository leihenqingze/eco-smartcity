package com.eco.wisdompark.domain.dto.req.consumeRecord;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="非训练局职工消费记录", description="非训练局职工消费记录")
public class NotTrainingStaffConsumeRecordDto {


    @ApiModelProperty(value = "二级部门id")
    private Integer deptId;

    @ApiModelProperty(value = "消费类型,0:用餐，1:消费")
    private Integer consomeType;

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
