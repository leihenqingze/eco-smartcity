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
 * pos机
 * </p>
 *
 * @author litao
 * @since 2018-12-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_pos")
@ApiModel(value="Pos对象", description="pos机")
public class Pos extends Model<Pos> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "POS机Id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "POS机编号")
    private String posNum;

    @ApiModelProperty(value = "POS机位置:1东职,2西职，3中心，4购物")
    private Boolean posPosition;

    @ApiModelProperty(value = "POS机消费类型：1用餐，2购物")
    private Boolean posConsumeType;

    @ApiModelProperty(value = "POS机部署状态：1已部署，2未部署，3已下线")
    private Boolean posArrangeStatus;

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
