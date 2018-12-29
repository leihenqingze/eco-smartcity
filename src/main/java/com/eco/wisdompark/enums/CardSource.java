package com.eco.wisdompark.enums;

/**
 * @author litao, 2018/12/29
 * @version 1.0
 */
public enum CardSource implements CommonEnum<CardSource> {
    TOP_UP(0, "充值"),
    CONSUMPTION(1, "消费"),
    SUBSIDIES(2, "补助"),
    ERROR(3, "差错"),
    RETURN_CARD(4, "退卡");

    private final int code;
    private final String description;

    CardSource(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static CardSource valueOf(int code) {
        for (CardSource item : CardSource.values()) {
            if (item.getCode() == code) {
                return item;
            }
        }
        throw new IllegalArgumentException("未知的");
    }

}
