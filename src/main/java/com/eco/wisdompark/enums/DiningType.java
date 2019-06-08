package com.eco.wisdompark.enums;

import com.eco.wisdompark.common.dto.ResponseData;
import com.eco.wisdompark.common.exceptions.WisdomParkException;
import com.eco.wisdompark.strategy.consume.Dining;

import java.time.LocalTime;
import java.util.function.Function;

/**
 * 用餐类型枚举
 *
 * @author litao, 2018/12/29
 * @version 1.0
 */
public enum DiningType implements CommonEnum<DiningType> {
    BREAKFAST(0, "早餐", LocalTime.of(5, 0), LocalTime.of(9, 59), ConsumeIdentity::getBreakfast),
    LUNCH(1, "午餐", LocalTime.of(10, 0), LocalTime.of(15, 59), ConsumeIdentity::getLunch),
    DINNER(2, "晚餐", LocalTime.of(16, 0), LocalTime.of(22, 59), ConsumeIdentity::getDinner);

    private final int code;
    private final String description;
    /**
     * 用餐开始时间
     */
    private final LocalTime start;
    /**
     * 用餐结束时间
     */
    private final LocalTime end;

    private Function<ConsumeIdentity, Dining> diningFunction;

    DiningType(int code, String description, LocalTime start, LocalTime end, Function<ConsumeIdentity, Dining> diningFunction) {
        this.code = code;
        this.description = description;
        this.start = start;
        this.end = end;
        this.diningFunction = diningFunction;
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

    /**
     * 通过时间获取用餐类型
     *
     * @param time 当前时间
     * @return 用餐类型
     */
    public static DiningType valueOf(LocalTime time) {
        for (DiningType item : DiningType.values()) {
            if (time.isAfter(item.getStart()) &&
                    time.isBefore(item.getEnd())) {
                return item;
            }
        }
        throw new WisdomParkException(ResponseData.STATUS_CODE_470, "该时间段不可以用餐");
    }

    /**
     * 根据消费身份获取当前用餐类型下的用餐对象
     *
     * @param consumeIdentity 消费身份
     * @return 用餐对象
     */
    public Dining getDining(ConsumeIdentity consumeIdentity) {
        return diningFunction.apply(consumeIdentity);
    }

}