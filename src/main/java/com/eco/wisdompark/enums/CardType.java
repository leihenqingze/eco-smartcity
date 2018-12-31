package com.eco.wisdompark.enums;

/**
 * @author litao, 2018/12/29
 * @version 1.0
 */
public enum CardType implements CommonEnum<CardType> {
    ID(0, "ID卡"),
    IC(1, "IC卡"),
    CPU(2, "CPU卡"),
    VIRTUAL(3, "虚拟卡");

    private final int code;
    private final String description;

    CardType(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static CardType valueOf(int code) {
        for (CardType item : CardType.values()) {
            if (item.getCode() == code) {
                return item;
            }
        }
        throw new IllegalArgumentException("未知的");
    }

}
