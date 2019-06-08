package com.eco.wisdompark.strategy.consume;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalTime;

/**
 * 用餐对象
 */
@Data
public class Dining {

    public Dining(double amount, LocalTime start, LocalTime end) {
        this(amount, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, start, end);
    }

    public Dining(double amount, double limitationAmount, int limitationTime, LocalTime start, LocalTime end) {
        this(amount, limitationAmount, limitationTime, Integer.MAX_VALUE, start, end);
    }

    public Dining(double amount, int overruTime, LocalTime start, LocalTime end) {
        this(amount, Integer.MAX_VALUE, Integer.MAX_VALUE, overruTime, start, end);
    }

    public Dining(double amount, double limitationAmount, int limitationTime, int overruTime, LocalTime start, LocalTime end) {
        this.amount = new BigDecimal(amount);
        this.limitationAmount = new BigDecimal(limitationAmount);
        this.limitationTime = limitationTime;
        this.overruTime = overruTime;
        this.start = start;
        this.end = end;
    }

    /**
     * 正常消费金额
     */
    private BigDecimal amount;
    /**
     * 超过正常消费次数的金额
     */
    private BigDecimal limitationAmount;
    /**
     * 非正常消费次数
     */
    private int limitationTime;
    /**
     * 不可消费次数
     */
    private int overruTime;
    /**
     * 开始用餐时间
     */
    private LocalTime start;
    /**
     * 结束用餐时间
     */
    private LocalTime end;

}