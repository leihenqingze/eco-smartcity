package com.eco.wisdompark.enums;

/**
 * @author litao, 2018/12/29
 * @version 1.0
 */
public enum ReportLossStstus implements CommonEnum<ReportLossStstus> {
    IN_USE(1, "在用"),
    REPORT_LOSS(2, "挂失");

    private final int code;
    private final String description;

    ReportLossStstus(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static ReportLossStstus valueOf(int code) {
        for (ReportLossStstus item : ReportLossStstus.values()) {
            if (item.getCode() == code) {
                return item;
            }
        }
        throw new IllegalArgumentException("未知的");
    }

}
