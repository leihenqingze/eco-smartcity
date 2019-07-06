package com.eco.wisdompark.domain.dto.req.subsidyRecord;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@ApiModel(value="CPU卡补助记录", description="CPU卡-补助记录表")
public class SubsidyRecordDto {

    @ApiModelProperty(value = "卡ID")
    private String cardId;

    @ApiModelProperty(value = "卡序列号")
    private String cardSerialNo;

    @ApiModelProperty(value = "用户ID")
    private Integer userId;

    @ApiModelProperty(value = "用户姓名")
    private String userName;

    @ApiModelProperty(value = "部门名称")
    private String deptName;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "补助金额")
    private BigDecimal amount;

    @ApiModelProperty(value = "补助类型：0手动，1自动, 2批量导入")
    private Integer type;

    @ApiModelProperty(value = "创建时间")
    private String createTime;

    @ApiModelProperty(value = "时间戳")
    private LocalDateTime ts;

    @ApiModelProperty(value = "补助前补助总金额")
    private BigDecimal subsidyAgoAmount;

    @ApiModelProperty(value = "补助后补助总金额")
    private BigDecimal subsidyAfterAmount;

}
