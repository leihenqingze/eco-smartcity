package com.eco.wisdompark.domain.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

 /**
  * 班车
  *
  * @author zhangkai
  * @date 2019/3/24 上午11:49
  */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_bus")
@ApiModel(value = "Bus对象", description = "班车")
public class Bus extends Model<Bus> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "班车Id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "班车车牌号")
    private String busNum;

    @ApiModelProperty(value = "车辆所属组织")
    private String busOwner;

    @ApiModelProperty(value = "车型")
    private String busSign;

    @ApiModelProperty(value = "登记日期")
    private LocalDate registerDate;

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
