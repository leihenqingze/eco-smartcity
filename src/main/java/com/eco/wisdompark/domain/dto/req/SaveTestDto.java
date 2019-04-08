package com.eco.wisdompark.domain.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.sf.oval.constraint.Length;

@Data
@ApiModel(value="Test对象", description="")
public class SaveTestDto {

    @ApiModelProperty(value = "设备名称")
    private String name;

    @ApiModelProperty(value = "卡片ID")
    private String card_id;

}
