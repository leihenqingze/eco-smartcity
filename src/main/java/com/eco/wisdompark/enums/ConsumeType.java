package com.eco.wisdompark.enums;

/**
 * @author litao, 2018/12/29
 * @version 1.0
 */
public enum ConsumeType implements CommonEnum<ConsumeType> {
    DINING(0, "用餐"),
    SHOP(1, "购物");

    private final int code;
    private final String description;

    ConsumeType(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static ConsumeType valueOf(int code) {
        for (ConsumeType item : ConsumeType.values()) {
            if (item.getCode() == code) {
                return item;
            }
        }
        throw new IllegalArgumentException("未知的");
    }

}
