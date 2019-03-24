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
 * 班车乘车记录
 * </p>
 *
 * @author zhangkai
 * @since 2019-03-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_bus_record")
@ApiModel(value="BusRecord对象", description="班车乘车记录")
public class BusRecord extends Model<BusRecord> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "卡id")
    private String cardId;

    @ApiModelProperty(value = "卡序列号")
    @TableField("card_serialNo")
    private String cardSerialno;

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "班车id")
    private Integer busId;

    @ApiModelProperty(value = "班车车牌号")
    private String busNum;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "逻辑删除：0正常，1删除")
    private Boolean del;

    private LocalDateTime ts;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
