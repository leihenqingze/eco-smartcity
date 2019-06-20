package com.eco.wisdompark.strategy.subsidy;

import java.math.BigDecimal;

public interface SubsidyStrategy {

    /**
     * 补助计算接口
     *
     * @param amount  补助前金额
     * @param subsidy 补助金额
     * @return 补助后金额
     */
    BigDecimal subsidy(BigDecimal amount, BigDecimal subsidy);

}