package com.eco.wisdompark.domain.dto.req.subsidyRecord;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


import java.math.BigDecimal;
import java.time.LocalDateTime;
@Data
@ApiModel(value="CPU卡补助记录", description="CPU卡-补助记录表")
public class SubsidyRecordDto {

    @ApiModelProperty(value = "卡ID")
    private String cardId;

    @ApiModelProperty(value = "卡序列号")
    @TableField("card_serialNo")
    private String cardSerialNo;

    @ApiModelProperty(value = "用户ID")
    private Integer userId;

    @ApiModelProperty(value = "补助金额")
    private BigDecimal amount;

    @ApiModelProperty(value = "补助类型，0：自动，1：手动")
    private Integer type;

    @ApiModelProperty(value = "创建时间")
    private String createTime;

    @ApiModelProperty(value = "时间戳")
    private LocalDateTime ts;

}
