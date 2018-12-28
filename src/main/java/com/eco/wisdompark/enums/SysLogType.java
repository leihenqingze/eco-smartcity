package com.eco.wisdompark.enums;

/**
 * @author litao, 2018/12/29
 * @version 1.0
 */
public enum SysLogType implements CommonEnum<SysLogType> {
    QUERY(0, "查询"),
    ADD(1, "新增"),
    MODIFY(2, "修改"),
    DELETE(3, "删除");

    private final int code;
    private final String description;

    SysLogType(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static SysLogType valueOf(int code) {
        for (SysLogType item : SysLogType.values()) {
            if (item.getCode() == code) {
                return item;
            }
        }
        throw new IllegalArgumentException("未知的");
    }

}
