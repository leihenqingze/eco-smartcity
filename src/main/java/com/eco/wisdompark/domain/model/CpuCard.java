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
 * CPU卡
 * </p>
 *
 * @author litao
 * @since 2018-12-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_cpu_card")
@ApiModel(value="CpuCard对象", description="CPU卡")
public class CpuCard extends Model<CpuCard> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "CPU卡表的自增Id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "CUP卡物理Id")
    private String cardId;

    @ApiModelProperty(value = "CUP卡面印刷的序列号")
    @TableField("card_serialNo")
    private String cardSerialno;

    @ApiModelProperty(value = "用户Id")
    private Integer userId;

    @ApiModelProperty(value = "CUP卡押金")
    private BigDecimal deposit;

    @ApiModelProperty(value = "CUP卡挂失状态：1在用，2挂失")
    private Integer reportLossStstus;

    @ApiModelProperty(value = "CUP卡类型，1ID，2IC，3CPU,4虚拟卡")
    private Integer cardType;

    @ApiModelProperty(value = "CUP卡来源，1制卡，2激活")
    private Integer cardSource;

    @ApiModelProperty(value = "CUP卡充值余额")
    private BigDecimal rechargeBalance;

    @ApiModelProperty(value = "CUP卡补助余额")
    private BigDecimal subsidyBalance;

    @ApiModelProperty(value = "逻辑删除：0正常，1删除")
    private Integer del;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "时间戳")
    private LocalDateTime ts;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
