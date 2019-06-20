package com.eco.wisdompark.strategy.subsidy;

import java.math.BigDecimal;

/**
 * 清零
 */
public class Reset implements SubsidyStrategy {

    @Override
    public BigDecimal subsidy(BigDecimal amount, BigDecimal subsidy) {
        return subsidy;
    }

}