package com.eco.wisdompark.domain.dto.req.card;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.sf.oval.constraint.NotNull;

@Data
@ApiModel(value="卡片激活Dto", description="卡片激活Dto")
public class ActiveCpuCardDto {

    @ApiModelProperty(value = "CUP卡物理Id")
    @NotNull(message = "CUP卡信息未读取成功")
    private String cardId;

    @ApiModelProperty(value = "CUP卡面印刷的序列号")
    @NotNull(message = "CUP卡序列号不能为空")
    private String cardSerialNo;

    @ApiModelProperty(value = "人员姓名")
    @NotNull(message = "人员姓名不能为空")
    private String userName;

    @ApiModelProperty(value = "人员手机号")
    @NotNull(message = "人员手机号不能为空")
    private String phoneNum;

    @ApiModelProperty(value = "人员身份证号")
    @NotNull(message = "人员身份证号不能为空")
    private String userCardNum;

    @ApiModelProperty(value = "组织架构Id")
    private Integer deptId;

}
