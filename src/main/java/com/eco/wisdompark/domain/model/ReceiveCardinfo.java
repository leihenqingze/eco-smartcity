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
 * 
 * </p>
 *
 * @author litao
 * @since 2019-07-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_receive_cardInfo")
@ApiModel(value="ReceiveCardinfo对象", description="")
public class ReceiveCardinfo extends Model<ReceiveCardinfo> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "接收数据卡片信息记录Id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "卡id")
    @TableField("itemId")
    private String itemId;

    @ApiModelProperty(value = "卡片编号")
    private String idno;

    @ApiModelProperty(value = "人员id")
    @TableField("personId")
    private String personId;

    @ApiModelProperty(value = "人员编号")
    @TableField("personCode")
    private String personCode;

    @ApiModelProperty(value = "人员名称")
    @TableField("personName")
    private String personName;

    @ApiModelProperty(value = "车牌号")
    @TableField("carNumber")
    private String carNumber;

    @ApiModelProperty(value = "停车场名称")
    @TableField("parkName")
    private String parkName;

    @ApiModelProperty(value = "停车场编号")
    @TableField("parkCode")
    private String parkCode;

    @ApiModelProperty(value = "卡介质类型")
    @TableField("mediaType")
    private String mediaType;

    @ApiModelProperty(value = "卡状态")
    private String status;

    @ApiModelProperty(value = "有效期开始时间")
    @TableField("startTime")
    private String startTime;

    @ApiModelProperty(value = "endTime")
    @TableField("endTime")
    private String endTime;

    @ApiModelProperty(value = "卡类型")
    @TableField("cardType")
    private String cardType;

    @ApiModelProperty(value = "卡余额")
    private String balance;

    @ApiModelProperty(value = "操作时间")
    @TableField("operateTime")
    private String operateTime;

    @ApiModelProperty(value = "操作员姓名")
    @TableField("operateName")
    private String operateName;

    @ApiModelProperty(value = "操作金额")
    @TableField("operateMoney")
    private String operateMoney;

    @ApiModelProperty(value = "备用字段")
    private String attach;

    @ApiModelProperty(value = "记录创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "时间戳")
    private LocalDateTime ts;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
