package com.eco.wisdompark.domain.dto.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "消费返回消息体", description = "消费返回消息体")
public class ConsumeRespDto {

    @ApiModelProperty(value = "人员姓名")
    private String userName;
    @ApiModelProperty(value = "部门名称")
    private String deptName;
    @ApiModelProperty(value = "消费金额")
    private String amount;
    @ApiModelProperty(value = "账户余额")
    private String balance;
    @ApiModelProperty(value = "下次扣款情况")
    private String nextConsume;

}