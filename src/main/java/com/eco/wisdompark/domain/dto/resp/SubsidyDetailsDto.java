package com.eco.wisdompark.domain.dto.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@ApiModel(value = "自动补助详情对象", description = "自动补助详情对象")
public class SubsidyDetailsDto {

    @ApiModelProperty(value = "部门名称")
    private String deptName;

    @ApiModelProperty(value = "补助时间")
    private LocalDateTime subsidyTime;

    @ApiModelProperty(value = "补助金额")
    private BigDecimal subsidyAmount;

    @ApiModelProperty(value = "补助记录")
    private List<SubsidyRecordListRespDto> subsidyRecords;

}
