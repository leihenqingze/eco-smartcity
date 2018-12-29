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
 * CPU卡-消费记录表
 * </p>
 *
 * @author litao
 * @since 2018-12-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_consume_record")
@ApiModel(value="ConsumeRecord对象", description="CPU卡-消费记录表")
public class ConsumeRecord extends Model<ConsumeRecord> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "卡ID")
    private Integer cardId;

    @ApiModelProperty(value = "卡序列号（冗余字段）")
    @TableField("card_serialNo")
    private String cardSerialno;

    @ApiModelProperty(value = "用户ID")
    private Integer userId;

    @ApiModelProperty(value = "消费充值金额")
    private BigDecimal rechargeAmount;

    @ApiModelProperty(value = "消费补助金额")
    private BigDecimal subsidyAmount;

    @ApiModelProperty(value = "POS机编号")
    private Integer posId;

    @ApiModelProperty(value = "消费类型，1：用餐，2：购物")
    private Integer type;

    @ApiModelProperty(value = "用餐类型，1：早餐，2：午餐，3：晚餐")
    private Integer diningType;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "时间戳")
    private LocalDateTime ts;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
