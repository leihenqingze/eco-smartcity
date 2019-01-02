package com.eco.wisdompark.domain.dto.req.card;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="挂失卡片查询确认信息Dto", description="挂失卡片查询确认信息Dto")
public class LossQueryConfirmDto {

    /**
     *  tb_cpu_card 主键id
     */
    @ApiModelProperty(value = "CPU卡信息表ID")
    private Integer cardId;

}
