package com.eco.wisdompark.domain.dto.req.consumeRecord;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Data
@ApiModel(value="消费记录", description="消费记录")
public class ConsumeRecordDto {

    @ApiModelProperty(value = "卡ID")
    private String cardId;

    @ApiModelProperty(value = "卡序列号（冗余字段）")
    private String cardSerialNo;

    @ApiModelProperty(value = "用户ID")
    private Integer userId;

    @ApiModelProperty(value = "消费金额")
    private BigDecimal amount;

    @ApiModelProperty(value = "消费充值金额")
    private BigDecimal rechargeAmount;

    @ApiModelProperty(value = "消费补助金额")
    private BigDecimal subsidyAmount;

    @ApiModelProperty(value = "POS机编号")
    private String posNum;

    @ApiModelProperty(value = "消费类型，0：用餐，1：购物")
    private Integer type;

    @ApiModelProperty(value = "用餐类型，0：早餐，1：午餐，2：晚餐")
    private Integer diningType;

    @ApiModelProperty(value = "创建时间")
    private String createTime;

    @ApiModelProperty(value = "时间戳")
    private LocalDateTime ts;

    @ApiModelProperty(value = "人员姓名")
    private String userName;

}
