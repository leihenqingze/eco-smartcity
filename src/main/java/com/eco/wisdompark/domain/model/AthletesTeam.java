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
 * 运动员队伍表
 * </p>
 *
 * @author zhangkai
 * @since 2019-03-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_athletes_team")
@ApiModel(value="AthletesTeam对象", description="运动员队伍表")
public class AthletesTeam extends Model<AthletesTeam> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "队伍名称")
    private String teamName;

    @ApiModelProperty(value = "是否删除，0：未删除，1：已删除")
    private Boolean del;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    private LocalDateTime ts;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
