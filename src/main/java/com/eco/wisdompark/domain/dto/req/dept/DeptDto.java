package com.eco.wisdompark.domain.dto.req.dept;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ApiModel(value="组织架构", description="组织架构")
public class DeptDto {

    @ApiModelProperty(value = "组织架构ID")
    private Integer id;

    @ApiModelProperty(value = "上级ID")
    private Integer deptUpId;

    @ApiModelProperty(value = "上下级ID字符串")
    private String deptUpDownStr;

    @ApiModelProperty(value = "组织架构名称")
    private String deptName;

    @ApiModelProperty(value = "消费身份：1训练局职工，2非训练局职工，3保安，4保洁")
    private Boolean consumeIdentity;

    @ApiModelProperty(value = "逻辑删除：0正常，1删除")
    private Boolean del;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "时间戳")
    private LocalDateTime ts;
}
