package com.eco.wisdompark.domain.dto.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.sf.oval.constraint.NotNull;

import java.math.BigDecimal;

@Data
@ApiModel(value="制卡返回Dto", description="制卡返回Dto")
public class RespMakingCpuCardDto {

    @ApiModelProperty(value = "卡编号")
    private Integer id;

    @ApiModelProperty(value = "人员姓名")
    private String userName;

    @ApiModelProperty(value = "人员手机号")
    private String phoneNum;

    @ApiModelProperty(value = "人员身份证号")
    private String userCardNum;

    @ApiModelProperty(value = "组织架构Id")
    private Integer deptId;

    @ApiModelProperty(value = "CUP卡押金")
    private BigDecimal deposit;

}
