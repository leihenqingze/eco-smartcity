package com.eco.wisdompark.enums;

/**
 * @author litao, 2018/12/29
 * @version 1.0
 */
public enum PosPosition implements CommonEnum<PosPosition> {
    EAST(0, "东职"),
    WEST(1, "西职"),
    CENTER(2, "中心"),
    SHOP(3, "购物");

    private final int code;
    private final String description;

    PosPosition(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static PosPosition valueOf(int code) {
        for (PosPosition item : PosPosition.values()) {
            if (item.getCode() == code) {
                return item;
            }
        }
        throw new IllegalArgumentException("未知的");
    }

}
