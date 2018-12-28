package com.eco.wisdompark.domain.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 系统用户表
 * </p>
 *
 * @author litao
 * @since 2018-12-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_sys_user")
@ApiModel(value="SysUser对象", description="系统用户表")
public class SysUser extends Model<SysUser> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "系统用户Id")
    private Integer id;

    @ApiModelProperty(value = "系统用户名称")
    private String sysUserName;

    @ApiModelProperty(value = "系统用户手机号")
    private String sysUserPhone;

    @ApiModelProperty(value = "系统用户所属部门（膳食处，财务处，保卫处等）")
    private Boolean sysUserDepartment;

    @ApiModelProperty(value = "系统用户登录密码")
    private String sysUserPass;

    @ApiModelProperty(value = "逻辑删除")
    private Boolean del;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "时间戳")
    private LocalDateTime ts;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
