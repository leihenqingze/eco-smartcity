package com.eco.wisdompark.domain.dto.resp;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import java.util.Map;

@Data
@ApiModel(value = "批量制卡结果Dto", description = "批量制卡结果Dto")
public class BatchMarkingCardRespDto {

    private Map<Integer,String> errorRowInfo;

    private int successCount;

    private int errorCount;
}
