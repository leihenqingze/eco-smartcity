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
    private String cardSerialNo;

    @ApiModelProperty(value = "充值金额")
    private BigDecimal amount;

    @ApiModelProperty(value = "充值类型：1现金存款，2支票存款，3旧卡导入，4汇款")
    private String rechargeWay;

    @ApiModelProperty(value = "创建时间")
    private String createTime;

    @ApiModelProperty(value = "充值类型：0手动，1批量导入")
    private String rechargeType;

    @ApiModelProperty(value = "批量导入序列号")
    private String importSerialno;

    @ApiModelProperty(value = "用户ID")
    private Integer userId;

    @ApiModelProperty(value = "用户姓名")
    private String userName;

    @ApiModelProperty(value = "部门名称")
    private String deptName;

    @ApiModelProperty(value = "手机号")
    private String phone;
}
