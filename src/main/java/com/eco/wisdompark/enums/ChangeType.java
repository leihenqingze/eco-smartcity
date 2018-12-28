package com.eco.wisdompark.enums;

/**
 * @author litao, 2018/12/29
 * @version 1.0
 */
public enum ChangeType implements CommonEnum<ChangeType> {
    MANUAL(0, "手动"),
    AUTOMATIC(1, "自动");

    private final int code;
    private final String description;

    ChangeType(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static ChangeType valueOf(int code) {
        for (ChangeType item : ChangeType.values()) {
            if (item.getCode() == code) {
                return item;
            }
        }
        throw new IllegalArgumentException("未知的");
    }

}
