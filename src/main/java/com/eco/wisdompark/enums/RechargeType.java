package com.eco.wisdompark.enums;

/**
 * @author litao, 2018/12/29
 * @version 1.0
 */
public enum RechargeType implements CommonEnum<RechargeType> {
    MANUAL(0, "手动"),
    BULK_IMPORT(1, "批量导入");

    private final int code;
    private final String description;

    RechargeType(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static RechargeType valueOf(int code) {
        for (RechargeType item : RechargeType.values()) {
            if (item.getCode() == code) {
                return item;
            }
        }
        throw new IllegalArgumentException("未知的");
    }

}
