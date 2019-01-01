package com.eco.wisdompark.domain.dto.req.rechargeRecord;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Data
@ApiModel(value="CPU卡充值记录", description="CPU卡充值记录")
public class RechargeRecordDto {

    @ApiModelProperty(value = "卡ID")
    private String cardId;

    @ApiModelProperty(value = "卡序列号")
    private String cardSerialno;

    @ApiModelProperty(value = "充值金额")
    private BigDecimal amount;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "充值类型：0手动，1批量导入")
    private Integer rechargeType;

    @ApiModelProperty(value = "批量导入序列号")
    private String importSerialno;

    @ApiModelProperty(value = "用户ID")
    private Integer userId;

    @ApiModelProperty(value = "时间戳")
    private LocalDateTime ts;
}
