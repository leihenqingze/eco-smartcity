package com.eco.wisdompark.domain.dto.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel(value = "批量补助信息Dto", description = "批量补助信息Dto")
public class BatchImportSubsidyDto {

    /**
     * 卡序列号
     */
    @ApiModelProperty(value = "CPU卡面序列号")
    private String cardSerialNo;

    /**
     * 补助金额
     */
    @ApiModelProperty(value = "补助金额")
    private BigDecimal subsidyAmt;

    @ApiModelProperty(value = "sheet名称")
    private String sheet;

    @ApiModelProperty(value = "行号")
    private int rowNum;

    @ApiModelProperty(value = "错误原因")
    private String errorMsg;

}