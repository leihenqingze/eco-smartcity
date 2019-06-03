package com.eco.wisdompark.domain.dto.req.card;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.sf.oval.constraint.Length;
import net.sf.oval.constraint.Min;
import net.sf.oval.constraint.NotNull;
import java.math.BigDecimal;

@Data
@ApiModel(value="批量制卡", description="批量制卡")
public class BatchMakingCpuCardDto{

    @ApiModelProperty(value = "CPU卡物理Id")
    @NotNull(message = "CPU卡信息未读取成功")
    @Length(max = 50,message = "CPU卡物理Id长度不能超过50位")
    private String cardId;

    @ApiModelProperty(value = "CPU卡面印刷的序列号")
    @NotNull(message = "CUP卡序列号不能为空")
    @Length(max = 50,message = "CUP卡序列号长度不能超过50位")
    private String cardSerialNo;

    @ApiModelProperty(value = "CPU卡押金")
    @Min(value = 0, message = "CPU卡押金需>=0元")
    private BigDecimal deposit = BigDecimal.ZERO;

    @ApiModelProperty(value = "人员姓名")
    @NotNull(message = "人员姓名不能为空")
    @Length(max = 15,message = "人员姓名长度不能超过50位")
    private String userName;

    @ApiModelProperty(value = "人员手机号")
    @Length(min = 11,max = 11,message = "人员手机号长度只能为11位")
    private String phoneNum;

    @ApiModelProperty(value = "人员身份证号")
    @Length(min = 15,max = 18,message = "人员身份证号长度必须为15位到18位")
    private String userCardNum;

    @ApiModelProperty(value = "组织架构Id")
    private Integer deptId;

    @ApiModelProperty(value = "CPU卡充值金额")
    @Min(value = 0, message = "CPU卡充值金额>=0元")
    private BigDecimal rechargeBalance;

}
