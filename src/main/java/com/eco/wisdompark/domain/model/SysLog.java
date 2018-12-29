package com.eco.wisdompark.domain.model;

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
 * 系统日志表
 * </p>
 *
 * @author litao
 * @since 2018-12-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_sys_log")
@ApiModel(value="SysLog对象", description="系统日志表")
public class SysLog extends Model<SysLog> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "系统日志Id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "系统用户ID")
    private Integer sysUserId;

    @ApiModelProperty(value = " 系统日志类型：1制卡、2激活、3挂失、4充值、" +
            "5退卡、6删除组织架构、7添加补助规则、8登录")
    private Integer sysLogType;

    @ApiModelProperty(value = "备注")
    private String sysRemark;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
