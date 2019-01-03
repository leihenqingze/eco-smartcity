package com.eco.wisdompark.enums;

/**
 * @author litao, 2018/12/29
 * @version 1.0
 */
public enum ConsumeIdentity implements CommonEnum<ConsumeIdentity> {
    TB_STAFF(0, "训练局职工"),
    UN_TB_STAFF(1, "非训练局职工"),
    GD(2, "保洁"),
    PAC(3, "保安"),
    PROPERTY(4,"物业");

    private final int code;
    private final String description;

    ConsumeIdentity(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static ConsumeIdentity valueOf(int code) {
        for (ConsumeIdentity item : ConsumeIdentity.values()) {
            if (item.getCode() == code) {
                return item;
            }
        }
        throw new IllegalArgumentException("未知的");
    }

}
