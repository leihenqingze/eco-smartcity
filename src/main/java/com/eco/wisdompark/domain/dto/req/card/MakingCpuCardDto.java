package com.eco.wisdompark.domain.dto.req.card;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.sf.oval.constraint.Length;
import net.sf.oval.constraint.Min;
import net.sf.oval.constraint.NotNull;

import java.math.BigDecimal;

@Data
@ApiModel(value="制卡", description="制卡")
public class MakingCpuCardDto {

    @ApiModelProperty(value = "CPU卡物理Id")
    @NotNull(message = "CPU卡信息未读取成功")
    @Length(max = 50,message = "CPU卡物理Id长度不能超过50位")
    private String cardId;

    @ApiModelProperty(value = "CPU卡面印刷的序列号")
    @NotNull(message = "CUP卡序列号不能为空")
    @Length(max = 50,message = "CUP卡序列号长度不能超过50位")
    private String cardSerialNo;

    @ApiModelProperty(value = "CPU卡押金")
    @NotNull(message = "CPU卡押金不能为空")
    @Min(value = 0, message = "CPU卡押金需>=0元")
    private BigDecimal deposit;

    @ApiModelProperty(value = "人员姓名")
    @NotNull(message = "人员姓名不能为空")
    @Length(max = 15,message = "人员姓名长度不能超过50位")
    private String userName;

    @ApiModelProperty(value = "人员手机号")
    @NotNull(message = "人员手机号不能为空")
    @Length(min = 11,max = 11,message = "人员手机号长度只能为11位")
    private String phoneNum;

    @ApiModelProperty(value = "人员身份证号")
    @NotNull(message = "人员身份证号不能为空")
    @Length(min = 15,max = 18,message = "人员身份证号长度必须为15位到18位")
    private String userCardNum;

    @ApiModelProperty(value = "组织架构Id")
    private Integer deptId;

}
