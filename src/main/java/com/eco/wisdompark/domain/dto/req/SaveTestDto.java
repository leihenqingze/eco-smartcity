package com.eco.wisdompark.domain.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.sf.oval.constraint.Length;

@Data
@ApiModel(value="Test对象", description="")
public class SaveTestDto {

    @ApiModelProperty(value = "设备名称")
    @Length(min = 10, max = 20, message = "设备名称在10到20个字符之间")
    private String name;

}
