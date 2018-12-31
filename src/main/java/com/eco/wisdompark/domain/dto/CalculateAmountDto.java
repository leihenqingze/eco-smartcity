package com.eco.wisdompark.domain.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 计算用餐费用和消费次数的返回体
 */
@Data
public class CalculateAmountDto {

    /**
     * 消费金额
     */
    private BigDecimal amount;
    /**
     * 消费次数
     */
    private Integer consumeTime;

}