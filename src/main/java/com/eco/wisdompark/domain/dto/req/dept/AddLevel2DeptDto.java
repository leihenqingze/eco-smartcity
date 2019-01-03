package com.eco.wisdompark.domain.dto.req.dept;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NonNull;
import net.sf.oval.constraint.NotNull;

@Data
@ApiModel(value="添加组织架构二级", description="添加组织架构二级")
public class AddLevel2DeptDto {

    @ApiModelProperty(value = "组织架构一级ID")
    @NotNull( message = "组织架构一级Id不能为空")
    private Integer id;

    @ApiModelProperty(value = "组织架构名称")
    private String deptName;

    @ApiModelProperty(value = "消费身份")
    private Integer consumeIdentity;


}
