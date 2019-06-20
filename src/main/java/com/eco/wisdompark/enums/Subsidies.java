package com.eco.wisdompark.enums;

import com.eco.wisdompark.strategy.subsidy.AddUp;
import com.eco.wisdompark.strategy.subsidy.Reset;
import com.eco.wisdompark.strategy.subsidy.SubsidyStrategy;
import lombok.Getter;

/**
 * @author litao, 2018/12/29
 * @version 1.0
 */
public enum Subsidies implements CommonEnum<Subsidies> {
    ADDUP(0, "累加", new AddUp()),
    RESET(1, "清零", new Reset());

    @Getter
    private final int code;
    @Getter
    private final String description;
    @Getter
    private final SubsidyStrategy subsidyStrategy;

    Subsidies(int code, String description, SubsidyStrategy subsidyStrategy) {
        this.code = code;
        this.description = description;
        this.subsidyStrategy = subsidyStrategy;
    }

    public static Subsidies valueOf(int code) {
        for (Subsidies item : Subsidies.values()) {
            if (item.getCode() == code) {
                return item;
            }
        }
        throw new IllegalArgumentException("未知的");
    }

}