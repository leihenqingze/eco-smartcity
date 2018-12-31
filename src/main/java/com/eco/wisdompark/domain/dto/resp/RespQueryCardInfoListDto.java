package com.eco.wisdompark.domain.dto.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.List;

@Data
@ApiModel(value="CPU卡信息列表Dto", description="CPU卡信息列表Dto")
public class RespQueryCardInfoListDto {

    @ApiModelProperty(value = "用户卡信息列表")
    private List<RespQueryCardInfoDto> cardInfoList;

}
