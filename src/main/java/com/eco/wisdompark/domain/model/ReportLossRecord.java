package com.eco.wisdompark.domain.model;

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
 * 挂失记录
 * </p>
 *
 * @author litao
 * @since 2018-12-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_report_loss_record")
@ApiModel(value="ReportLossRecord对象", description="挂失记录")
public class ReportLossRecord extends Model<ReportLossRecord> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "挂失记录Id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "人员Id")
    private Integer userId;

    @ApiModelProperty(value = "旧CUP卡物理Id")
    private String oldCardId;

    @ApiModelProperty(value = "旧CUP卡面印刷的序列号")
    @TableField("old_card_serialNo")
    private String oldCardSerialNo;

    @ApiModelProperty(value = "新CUP卡物理Id")
    private String newCardId;

    @ApiModelProperty(value = "新CUP卡面印刷的序列号")
    @TableField("new_card_serialNo")
    private String newCardSerialNo;

    @ApiModelProperty(value = "挂失时间")
    private LocalDateTime reportLossTiem;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "时间戳")
    private LocalDateTime ts;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
