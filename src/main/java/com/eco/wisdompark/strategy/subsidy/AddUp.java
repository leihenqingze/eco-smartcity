package com.eco.wisdompark.strategy.subsidy;

import java.math.BigDecimal;

/**
 * 累加
 */
public class AddUp implements SubsidyStrategy {

    @Override
    public BigDecimal subsidy(BigDecimal amount, BigDecimal subsidy) {
        return amount.add(subsidy);
    }

}