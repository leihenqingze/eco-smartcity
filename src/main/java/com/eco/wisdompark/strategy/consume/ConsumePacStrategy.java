package com.eco.wisdompark.strategy.consume;

import com.eco.wisdompark.common.dto.ResponseData;
import com.eco.wisdompark.common.exceptions.WisdomParkException;
import com.eco.wisdompark.enums.DiningType;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * 保洁消费策略
 * 早餐5元，没有次数限制
 * 午餐和晚餐每天只能各刷一次，金额为0
 */
@Component
public class ConsumePacStrategy {

    /**
     * 根据用餐类型和消费次数获取消费金额
     *
     * @param diningType  用餐类型
     * @param consumeTime 消费次数
     * @return 消费金额
     */
    public BigDecimal consume(DiningType diningType, int consumeTime) {
        if (DiningType.BREAKFAST.equals(diningType))
            return new BigDecimal(5);
        else {
            if (consumeTime == 0) {
                return new BigDecimal(0);
            } else {
                throw new WisdomParkException(ResponseData.STATUS_CODE_471, "用餐次数超过限制");
            }
        }
    }

}