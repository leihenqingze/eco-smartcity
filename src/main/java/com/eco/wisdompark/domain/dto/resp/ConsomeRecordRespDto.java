package com.eco.wisdompark.domain.dto.resp;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.eco.wisdompark.domain.dto.req.consumeRecord.ConsumeRecordDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel(value = "消费记录统计", description = "消费记录统计")
public class ConsomeRecordRespDto {

    @ApiModelProperty(value = "当前页总金额")
    private BigDecimal currentPageAmount = BigDecimal.ZERO;

    @ApiModelProperty(value = "当前页税后总金额")
    private BigDecimal currentPageAfterTaxAmount = BigDecimal.ZERO;

    @ApiModelProperty(value = "总金额")
    private BigDecimal totalAmount = BigDecimal.ZERO;

    @ApiModelProperty(value = "税后总金额")
    private BigDecimal totalAfterTaxAmount = BigDecimal.ZERO;

    @ApiModelProperty(value = "分页信息")
    private IPage<ConsumeRecordDto> consumeRecordDtoPage;

}
