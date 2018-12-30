package com.eco.wisdompark.domain.dto.req.dept;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.sf.oval.constraint.NotNull;

@Data
@ApiModel(value="删除组织架构", description="删除组织架构")
public class DelDeptDto {

    @ApiModelProperty(value = "组织架构ID")
    @NotNull( message = "组织架构Id不能为空")
    private Integer id;
}
