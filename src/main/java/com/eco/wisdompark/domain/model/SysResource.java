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
 * 系统资源
 * </p>
 *
 * @author litao
 * @since 2018-12-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_sys_resource")
@ApiModel(value="SysResource对象", description="系统资源")
public class SysResource extends Model<SysResource> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "系统资源Id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "上级资源Id")
    private Integer resourceUpId;

    @ApiModelProperty(value = "资源code")
    private String resourceCode;

    @ApiModelProperty(value = "资源名称")
    private String resourceName;

    @ApiModelProperty(value = "系统编号")
    private String sysNum;

    @ApiModelProperty(value = "资源URL")
    private String resourceUrl;

    @ApiModelProperty(value = "逻辑删除：0正常，1删除")
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
