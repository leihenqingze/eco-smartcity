package com.eco.wisdompark.domain.dto.resp;

import com.eco.wisdompark.domain.dto.CpuCardBaseDto;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value="激活卡返回Dto", description="激活卡返回Dto")
public class RespActiveCpuCardDto extends CpuCardBaseDto {
}
