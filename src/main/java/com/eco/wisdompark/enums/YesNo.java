package com.eco.wisdompark.enums;

/**
 * @author litao, 2018/12/29
 * @version 1.0
 */
public enum YesNo implements CommonEnum<YesNo> {

    YES(0, "是"),
    NO(1, "否");

    private final int code;
    private final String description;

    YesNo(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static YesNo valueOf(int code) {
        for (YesNo item : YesNo.values()) {
            if (item.getCode() == code) {
                return item;
            }
        }
        throw new IllegalArgumentException("未知的");
    }

}
