package com.eco.wisdompark.domain.dto.req.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@ApiModel(value = "查询人员列表", description = "查询人员列表")
public class UserDto {

    @ApiModelProperty(value = "人员ID")
    private Integer id;

    @ApiModelProperty(value = "人员姓名")
    private String userName;

    @ApiModelProperty(value = "人员身份证号")
    private String userCardNum;

    @ApiModelProperty(value = "人员手机号")
    private String phoneNum;

    @ApiModelProperty(value = "组织架构Id")
    private Integer deptId;

    @ApiModelProperty(value = "组织架构名称")
    private String deptName;

    @ApiModelProperty(value = "逻辑删除")
    private Integer del;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "时间戳")
    private LocalDateTime ts;

    @ApiModelProperty(value = "CUP卡面印刷的序列号")
    private String cardSerialNo;

    @ApiModelProperty(value = "CUP卡押金")
    private BigDecimal deposit;

    @ApiModelProperty(value = "CUP卡来源，1制卡，2激活")
    private Integer cardSource;

    @ApiModelProperty(value = "CUP卡充值余额")
    private BigDecimal rechargeBalance;

    @ApiModelProperty(value = "CUP卡补助余额")
    private BigDecimal subsidyBalance;

    @ApiModelProperty(value = "CPU卡ID")
    private String cardId;

    @ApiModelProperty(value = "CPU卡是否停用:0启用，1停用")
    private int cardIfUsed;

}