package com.eco.wisdompark.domain.dto.req.dept;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NonNull;
import net.sf.oval.constraint.NotNull;

@Data
@ApiModel(value="添加组织架构一级", description="添加组织架构一级")
public class AddLevel1DeptDto {

    @ApiModelProperty(value = "组织架构名称")
    @NotNull( message = "组织架构不能为空")
    private String deptName;

    @ApiModelProperty(value = "消费身份：0训练局职工，1非训练局职工，2保安，3保洁, 4物业")
    @NotNull( message = "消费身份不能为空")
    private  Integer consumeIdentity;




}
