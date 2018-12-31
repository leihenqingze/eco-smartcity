package com.eco.wisdompark.domain.model;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;
import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 自动补助-补助规则
 * </p>
 *
 * @author litao
 * @since 2018-12-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_subsidy_rule")
@ApiModel(value = "SubsidyRule对象", description = "自动补助-补助规则")
public class SubsidyRule extends Model<SubsidyRule> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "补助规则Id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "组织架构ID")
    private Integer deptId;

    @ApiModelProperty(value = "补助时间")
    private LocalDateTime subsidyTime;

    @ApiModelProperty(value = "补助金额")
    private BigDecimal subsidyAmount;

    @ApiModelProperty(value = "补助状态（是否停止补助）")
    private Integer subsidyStatus;

    @ApiModelProperty(value = "逻辑删除")
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
