package com.eco.wisdompark.domain.dto.req.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NonNull;
import net.sf.oval.constraint.Length;
import net.sf.oval.constraint.NotNull;

@Data
@ApiModel(value="查询人员详情", description="查询人员详情")
public class GetUserDto {

    @ApiModelProperty(value = "人员ID")
    @NotNull( message = "人员Id不能空")
    private Integer id;

}
