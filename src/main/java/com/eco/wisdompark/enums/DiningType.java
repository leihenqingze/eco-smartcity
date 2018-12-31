package com.eco.wisdompark.enums;

import com.eco.wisdompark.common.exceptions.WisdomParkException;

import java.time.LocalTime;

/**
 * @author litao, 2018/12/29
 * @version 1.0
 */
public enum DiningType implements CommonEnum<DiningType> {
    BREAKFAST(0, "早餐", LocalTime.of(6, 0), LocalTime.of(10, 0)),
    LUNCH(1, "午餐", LocalTime.of(11, 0), LocalTime.of(14, 0)),
    DINNER(2, "晚餐", LocalTime.of(16, 0), LocalTime.of(20, 0));

    private final int code;
    private final String description;
    private final LocalTime start;
    private final LocalTime end;

    DiningType(int code, String description, LocalTime start, LocalTime end) {
        this.code = code;
        this.description = description;
        this.start = start;
        this.end = end;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public LocalTime getStart() {
        return start;
    }

    public LocalTime getEnd() {
        return end;
    }

    public static DiningType valueOf(int code) {
        for (DiningType item : DiningType.values()) {
            if (item.getCode() == code) {
                return item;
            }
        }
        throw new IllegalArgumentException("未知的");
    }

    public static DiningType valueOf(LocalTime time) {
        for (DiningType item : DiningType.values()) {
            if (time.isAfter(item.getStart()) &&
                    time.isBefore(item.getEnd())) {
                return item;
            }
        }
        throw new WisdomParkException(400, "该时间段不可以用餐");
    }

}
