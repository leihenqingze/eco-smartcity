package com.eco.wisdompark.enums;

/**
 * @author litao, 2018/12/29
 * @version 1.0
 */
public enum AmountChangeType implements CommonEnum<AmountChangeType> {
    TOP_UP(0, "充值"),
    RECHARGE_AMOUNT(1, "消费充值余额"),
    SUBSIDY_AMOUNT(2, "消费补助金额"),
    SUBSIDIES(3, "补助"),
    RECHARGE_ERROR(4, "补差消费余额"),
    SUBSIDY_ERROR(5, "补差补助余额"),
    RETURN_CARD(6, "退卡");

    private final int code;
    private final String description;

    AmountChangeType(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static AmountChangeType valueOf(int code) {
        for (AmountChangeType item : AmountChangeType.values()) {
            if (item.getCode() == code) {
                return item;
            }
        }
        throw new IllegalArgumentException("未知的");
    }

}
