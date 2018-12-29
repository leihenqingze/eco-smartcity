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
 * CPU卡-充值记录表
 * </p>
 *
 * @author litao
 * @since 2018-12-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_recharge_record")
@ApiModel(value="RechargeRecord对象", description="CPU卡-充值记录表")
public class RechargeRecord extends Model<RechargeRecord> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "卡ID")
    private Integer cardId;

    @ApiModelProperty(value = "卡序列号")
    @TableField("card_serialNo")
    private String cardSerialno;

    @ApiModelProperty(value = "充值金额")
    private BigDecimal amount;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "充值类型：1手动，2批量导入")
    private Boolean rechargeType;

    @ApiModelProperty(value = "批量导入序列号")
    @TableField("import_serialNo")
    private String importSerialno;

    @ApiModelProperty(value = "用户ID")
    private Integer userId;

    @ApiModelProperty(value = "时间戳")
    private LocalDateTime ts;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
