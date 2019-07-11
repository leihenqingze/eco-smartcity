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
@TableName("tb_receive_personInfo")
@ApiModel(value="ReceivePersoninfo对象", description="")
public class ReceivePersoninfo extends Model<ReceivePersoninfo> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "推送人员信息记录ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "卡id")
    @TableField("itemId")
    private String itemId;

    @ApiModelProperty(value = "人员编号")
    private String code;

    @ApiModelProperty(value = "人员名称")
    private String name;

    @ApiModelProperty(value = "停车场名称")
    @TableField("parkName")
    private String parkName;

    @ApiModelProperty(value = "停车场编号")
    @TableField("parkCode")
    private String parkCode;

    @ApiModelProperty(value = "人员类型")
    private String type;

    @ApiModelProperty(value = "是否操作员")
    @TableField("isOperator")
    private String isOperator;

    @ApiModelProperty(value = "证件类型")
    @TableField("idCardType")
    private String idCardType;

    @ApiModelProperty(value = "证件编号")
    @TableField("idCardNo")
    private String idCardNo;

    @ApiModelProperty(value = "性别")
    private String sex;

    @ApiModelProperty(value = "年龄")
    private String age;

    @ApiModelProperty(value = "籍贯")
    @TableField("nativePlace")
    private String nativePlace;

    @ApiModelProperty(value = "联系电话")
    private String telephone;

    @ApiModelProperty(value = "办公室电话")
    @TableField("officeTel")
    private String officeTel;

    @ApiModelProperty(value = "联系地址")
    private String address;

    @ApiModelProperty(value = "电子邮件")
    private String email;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "状态")
    private String status;

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
