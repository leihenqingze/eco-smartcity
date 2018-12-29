package com.eco.wisdompark.domain.model;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 金额变动记录
 * </p>
 *
 * @author litao
 * @since 2018-12-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_change_amount")
@ApiModel(value="ChangeAmount对象", description="金额变动记录")
public class ChangeAmount extends Model<ChangeAmount> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "CUP卡物理Id")
    private String cardId;

    @ApiModelProperty(value = "CUP卡面印刷的序列号")
    @TableField("card_serialNo")
    private String cardSerialno;

    @ApiModelProperty(value = "人员Id")
    private Integer userId;

    @ApiModelProperty(value = "变动金额")
    private BigDecimal changeAmount;

    @ApiModelProperty(value = "变动前充值余额")
    private BigDecimal changeAgoRecharge;

    @ApiModelProperty(value = "变动后充值余额")
    private BigDecimal changeAfterRecharge;

    @ApiModelProperty(value = "变动前补助余额")
    private BigDecimal changeAgoSubsidy;

    @ApiModelProperty(value = "变动后补助余额")
    private BigDecimal changeAfterSubsidy;

    @ApiModelProperty(value = "金额变动类型:1充值，2消费充值余额，3消费补助金额，4补助，5补差消费余额，6补差补助余额，7退卡")
    private Boolean changeType;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    private LocalDateTime ts;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
