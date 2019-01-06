package com.eco.wisdompark.domain.dto.req.dept;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.sf.oval.constraint.NotNull;

@Data
public class GetLevel1DeptByIdentityDto {

    @ApiModelProperty(value = "消费身份")
    @NotNull
    private Integer consumeIdentity;
}
