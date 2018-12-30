package com.eco.wisdompark.domain.dto.req.card;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.sf.oval.constraint.Min;
import net.sf.oval.constraint.NotNull;

import java.math.BigDecimal;

@Data
@ApiModel(value="制卡", description="制卡")
public class MakingCpuCardDto {

    @ApiModelProperty(value = "CUP卡物理Id")
    @NotNull(message = "CUP卡信息未读取成功")
    private String cardId;

    @ApiModelProperty(value = "CUP卡面印刷的序列号")
    @NotNull(message = "CUP卡序列号不能为空")
    private String cardSerialno;

    @ApiModelProperty(value = "CUP卡押金")
    @NotNull(message = "CUP卡押金不能为空")
    @Min(value = 0, message = "CUP卡押金需>=0元")
    private BigDecimal deposit;

    @ApiModelProperty(value = "CUP卡来源，1制卡，2激活")
    private Integer cardSource;

    @ApiModelProperty(value = "人员姓名")
    @NotNull(message = "人员姓名不能为空")
    private String userName;

    @ApiModelProperty(value = "人员手机号")
    @NotNull(message = "人员手机号不能为空")
    private String phoneNum;

    @ApiModelProperty(value = "人员身份证号")
    @NotNull(message = "人员身份证号不能为空")
    private String userCardNum;

    @ApiModelProperty(value = "组织架构Id")
    private Integer deptId;

//    @ApiModelProperty(value = "CUP卡充值余额")
//    private BigDecimal rechargeBalance;
//
//    @ApiModelProperty(value = "CUP卡补助余额")
//    private BigDecimal subsidyBalance;

}
