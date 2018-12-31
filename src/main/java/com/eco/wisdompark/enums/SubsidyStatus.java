package com.eco.wisdompark.enums;

/**
 * 自动补助规则状态枚举
 * @author litao, 2018/12/31
 * @version 1.0
 */
public enum SubsidyStatus implements CommonEnum<SubsidyStatus> {
    START(0, "启动"),
    DISABLE(1, "停止");

    private final int code;
    private final String description;

    SubsidyStatus(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static SubsidyStatus valueOf(int code) {
        for (SubsidyStatus item : SubsidyStatus.values()) {
            if (item.getCode() == code) {
                return item;
            }
        }
        throw new IllegalArgumentException("未知的");
    }

}
