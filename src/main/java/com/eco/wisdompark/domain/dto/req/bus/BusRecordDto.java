package com.eco.wisdompark.domain.dto.req.bus;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@ApiModel(value="查询班车乘车记录", description="查询班车乘车记录")
public class BusRecordDto {

    @ApiModelProperty(value = "主键")
    private Integer id;

    @ApiModelProperty(value = "卡id")
    private String cardId;

    @ApiModelProperty(value = "卡序列号")
    private String cardSerialNo;

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "班车id")
    private Integer busId;

    @ApiModelProperty(value = "班车车牌号")
    private String busNum;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "运动员名称")
    private String userName;

    @ApiModelProperty(value = "运动员身份证号")
    private String userCardNum;

    @ApiModelProperty(value = "运动员所属队伍")
    private String team;

    @ApiModelProperty(value = "性别：0男，1女")
    private String gender;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "乘车地点")
    private String ridingAddress = "国家体育总局训练局";

    @ApiModelProperty(value = "乘车线路")
    private String ridingLine = "1号线路";

}
