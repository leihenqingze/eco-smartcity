package com.eco.wisdompark.domain.dto.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel(value = "卡片信息和人员信息", description = "卡片信息和人员信息")
public class RespCpuCardAndUserInfoDto {

    @ApiModelProperty(value = "人员ID")
    private Integer id;

    @ApiModelProperty(value = "人员姓名")
    private String userName;

    @ApiModelProperty(value = "组织架构名称")
    private String deptName;

    @ApiModelProperty(value = "CUP卡面印刷的序列号")
    private String cardSerialNo;


    @ApiModelProperty(value = "CUP卡充值余额")
    private BigDecimal rechargeBalance;

    @ApiModelProperty(value = "CUP卡补助余额")
    private BigDecimal subsidyBalance;

}
