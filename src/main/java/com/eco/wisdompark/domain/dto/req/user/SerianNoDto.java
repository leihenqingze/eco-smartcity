package com.eco.wisdompark.domain.dto.req.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "卡编号查询人员信息", description = "卡编号查询人员信息")
public class SerianNoDto {

    @ApiModelProperty(value = "CUP卡面印刷的序列号")
    private String cardSerialNo;

}
