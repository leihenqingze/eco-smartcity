package com.eco.wisdompark.enums;

import com.eco.wisdompark.strategy.consume.Dining;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author litao, 2018/12/29
 * @version 1.0
 */
@AllArgsConstructor
public enum ConsumeIdentity implements CommonEnum<ConsumeIdentity> {
    /**
     * 早餐5元，不限次数
     * 午餐、晚餐正常消费20，从第3次开始收费为29，无次数限制
     */
    TB_STAFF(0, "训练局职工",
            new Dining(5, DiningType.BREAKFAST.getStart(), DiningType.BREAKFAST.getEnd()),
            new Dining(20, 29, 3, DiningType.LUNCH.getStart(), DiningType.LUNCH.getEnd()),
            new Dining(20, 29, 3, DiningType.DINNER.getStart(), DiningType.DINNER.getEnd())),
    /**
     * 早餐6元，不限次数
     * 午餐、晚餐正常消费20，从第2次开始收费为29，无次数限制
     */
    UN_TB_STAFF(1, "非训练局职工",
            new Dining(6, DiningType.BREAKFAST.getStart(), DiningType.BREAKFAST.getEnd()),
            new Dining(20, 29, 2, DiningType.LUNCH.getStart(), DiningType.LUNCH.getEnd()),
            new Dining(20, 29, 2, DiningType.DINNER.getStart(), DiningType.DINNER.getEnd())),

    /**
     * 早餐5元，不限次数
     * 午餐、晚餐免费，午餐加晚餐只能消费一次
     */
    GD(2, "保洁",
            new Dining(5, DiningType.BREAKFAST.getStart(), DiningType.BREAKFAST.getEnd()),
            new Dining(0, 1, DiningType.LUNCH.getStart(), DiningType.DINNER.getEnd()),
            new Dining(0, 1, DiningType.LUNCH.getStart(), DiningType.DINNER.getEnd())),

    /**
     * 早餐5元，不限次数
     * 午餐免费，只能消费一次
     * 晚餐免费，只能消费一次
     */
    PAC(3, "保安",
            new Dining(5, DiningType.BREAKFAST.getStart(), DiningType.BREAKFAST.getEnd()),
            new Dining(0, 1, DiningType.LUNCH.getStart(), DiningType.LUNCH.getEnd()),
            new Dining(0, 1, DiningType.DINNER.getStart(), DiningType.DINNER.getEnd())),

    /**
     * 早餐5元，不限次数
     * 午餐、晚餐正常消费20，从第3次开始收费为29，无次数限制
     * 晚餐5元，不限次数
     */
    E(4, "用餐类型E",
            new Dining(5, DiningType.BREAKFAST.getStart(), DiningType.BREAKFAST.getEnd()),
            new Dining(20, 29, 3, DiningType.LUNCH.getStart(), DiningType.LUNCH.getEnd()),
            new Dining(5, DiningType.DINNER.getStart(), DiningType.DINNER.getEnd())),

    /**
     * 一日三餐都免费，每餐只能消费一次
     */
    F(5, "用餐类型F",
            new Dining(0, 1, DiningType.BREAKFAST.getStart(), DiningType.BREAKFAST.getEnd()),
            new Dining(0, 1, DiningType.LUNCH.getStart(), DiningType.LUNCH.getEnd()),
            new Dining(0, 1, DiningType.DINNER.getStart(), DiningType.DINNER.getEnd()));

    @Getter
    private final int code;
    @Getter
    private final String description;
    @Getter
    private final Dining breakfast;
    @Getter
    private final Dining lunch;
    @Getter
    private final Dining dinner;

    public static ConsumeIdentity valueOf(int code) {
        for (ConsumeIdentity item : ConsumeIdentity.values()) {
            if (item.getCode() == code) {
                return item;
            }
        }
        throw new IllegalArgumentException("未知的");
    }

}