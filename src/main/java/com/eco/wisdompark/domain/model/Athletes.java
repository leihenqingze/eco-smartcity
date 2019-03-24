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
 * 运动员信息表
 * </p>
 *
 * @author zhangkai
 * @since 2019-03-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_athletes")
@ApiModel(value="Athletes对象", description="运动员信息表")
public class Athletes extends Model<Athletes> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "运动员名称")
    private String userName;

    @ApiModelProperty(value = "运动员身份证号")
    private String userCardNum;

    @ApiModelProperty(value = "运动员所属队伍")
    private Integer teamId;

    @ApiModelProperty(value = "性别：0男，1女")
    private Integer gender;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "卡id")
    private String cardId;

    @ApiModelProperty(value = "卡序列号")
    @TableField("card_serialNo")
    private String cardSerialno;

    @ApiModelProperty(value = "是否删除标识，0：未删除，1：已删除")
    private Boolean del;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    private LocalDateTime ts;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
