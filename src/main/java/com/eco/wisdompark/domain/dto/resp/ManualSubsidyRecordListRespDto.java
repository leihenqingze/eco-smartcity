package com.eco.wisdompark.domain.dto.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel(value = "自动补助记录对象", description = "自动补助记录对象")
public class ManualSubsidyRecordListRespDto {

    @ApiModelProperty(value = "部门名称")
    private String deptName;

    @ApiModelProperty(value = "人员姓名")
    private String userName;

    @ApiModelProperty(value = "人员身份证号")
    private String userCardNum;

    @ApiModelProperty(value = "CUP卡面印刷的序列号")
    private String cardSerialNo;

    @ApiModelProperty(value = "补助金额")
    private BigDecimal subsidyAmount;

}