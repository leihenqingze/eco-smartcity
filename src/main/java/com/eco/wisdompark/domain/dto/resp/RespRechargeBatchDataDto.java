package com.eco.wisdompark.domain.dto.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@ApiModel(value="CPU卡批量充值信息核对返回Dto", description="CPU卡批量充值信息核对返回Dto")
public class RespRechargeBatchDataDto {

    @ApiModelProperty(value = "充值信息核对Dto集合")
    private List<BatchRechargeDataDto> batchRechargeDataDtoList;

    @ApiModelProperty(value = "本次充值总金额")
    private BigDecimal totalAmt;

    @ApiModelProperty(value = "文件code，确认充值时回传过来")
    private String fileCode;

    /**
     * Excel 中的 cardSerialNo 未找到卡信息的数据
     */
    @ApiModelProperty(value = "充值信息有误Dto集合")
    private List<BatchRechargeDataDto> infoErrorList;

    @ApiModelProperty(value = "成功条数")
    private int successCount;

    @ApiModelProperty(value = "失败条数")
    private int errorCount;

}
