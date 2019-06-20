package com.eco.wisdompark.domain.dto.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@ApiModel(value = "CPU卡批量补助信息核对返回Dto", description = "CPU卡批量补助信息核对返回Dto")
public class RespBatchImportSubsidyDto {

    @ApiModelProperty(value = "补助信息核对Dto集合")
    private List<BatchImportSubsidyDto> batchRechargeDataDtoList;

    @ApiModelProperty(value = "本次补助总金额")
    private BigDecimal totalAmt;

    /**
     * Excel 中的 异常数据
     */
    @ApiModelProperty(value = "补助信息有误Dto集合")
    private List<BatchImportSubsidyDto> infoErrorList;

    @ApiModelProperty(value = "成功条数")
    private int successCount;

    @ApiModelProperty(value = "失败条数")
    private int errorCount;

}