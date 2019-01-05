package com.eco.wisdompark.domain.dto.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel(value = "批量充值信息确认Dto", description = "批量充值信息确认Dto")
public class BatchRechargeDataDto {

    /**
     * 卡序列号
     */
    @ApiModelProperty(value = "CPU卡面序列号")
    private String cardSerialNo;

    /**
     * 充值金额
     */
    @ApiModelProperty(value = "充值金额")
    private BigDecimal rechargeAmt;

    @ApiModelProperty(value = "人员姓名")
    private String userName;

    @ApiModelProperty(value = "手机号")
    private String phoneNum;

    @ApiModelProperty(value = "组织架构ID")
    private Integer deptId;

    @ApiModelProperty(value = "身份证号")
    private String userCardNum;

    public BatchRechargeDataDto(String cardSerialNo, BigDecimal rechargeAmt) {
        this.cardSerialNo = cardSerialNo;
        this.rechargeAmt = rechargeAmt;
    }

    public BatchRechargeDataDto() {
    }
}
