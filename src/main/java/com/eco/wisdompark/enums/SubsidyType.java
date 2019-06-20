package com.eco.wisdompark.enums;

/**
 * @author litao, 2018/12/29
 * @version 1.0
 */
public enum SubsidyType implements CommonEnum<SubsidyType> {
    MANUAL(0, "手动"),
    AUTOMATIC(1, "自动"),
    BULKIMPORT(2, "批量导入");

    private final int code;
    private final String description;

    SubsidyType(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static SubsidyType valueOf(int code) {
        for (SubsidyType item : SubsidyType.values()) {
            if (item.getCode() == code) {
                return item;
            }
        }
        throw new IllegalArgumentException("未知的");
    }

}
