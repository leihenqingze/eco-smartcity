package com.eco.wisdompark.strategy.consume;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.eco.wisdompark.common.dto.ResponseData;
import com.eco.wisdompark.common.exceptions.WisdomParkException;
import com.eco.wisdompark.domain.dto.CalculateAmountDto;
import com.eco.wisdompark.domain.model.Dept;
import com.eco.wisdompark.enums.ConsumeIdentity;
import com.eco.wisdompark.enums.DiningType;
import com.eco.wisdompark.mapper.ConsumeRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * 体育局职工消费策略
 */
@Component
public class ConsumeCalculate {

    @Autowired
    private ConsumeRecordMapper consumeRecordMapper;

    /**
     * 根据部门和消费类型计算消费金额
     *
     * @param cardId     CPU卡物理ID
     * @param dept       部门
     * @param diningType 用餐类型
     * @return 计算用餐费用和消费次数的返回体
     */
    public CalculateAmountDto calculateAmount(String cardId, Dept dept, DiningType diningType)
            throws WisdomParkException {
        CalculateAmountDto calculateAmountDto = new CalculateAmountDto();
        ConsumeIdentity consumeIdentity = ConsumeIdentity.valueOf(dept.getConsumeIdentity());
        Dining dining = diningType.getDining(consumeIdentity);
        Integer consumeTime = countConsumeTime(cardId, dining);
        BigDecimal amount = calculateAmount(dining, consumeTime);
        calculateAmountDto.setAmount(amount);
        calculateAmountDto.setConsumeTime(consumeTime);
        return calculateAmountDto;
    }

    /**
     * 根据部门和消费类型计算消费金额
     *
     * @param dept        部门
     * @param diningType  用餐类型
     * @param consumeTime 用餐次数
     * @return 计算用餐费用和消费次数的返回体
     */
    public BigDecimal calculateAmount(Dept dept, DiningType diningType, int consumeTime)
            throws WisdomParkException {
        ConsumeIdentity consumeIdentity = ConsumeIdentity.valueOf(dept.getConsumeIdentity());
        Dining dining = diningType.getDining(consumeIdentity);
        return calculateAmount(dining, consumeTime);
    }

    /**
     * 根据用餐对象和已用餐次数计算消费金额
     *
     * @param dining      用餐对象
     * @param consumeTime 用餐次数
     * @return 用餐费用
     */
    private BigDecimal calculateAmount(Dining dining, int consumeTime) throws WisdomParkException {
        if (consumeTime > dining.getOverruTime()) {
            throw new WisdomParkException(ResponseData.STATUS_CODE_471, "用餐次数超过限制");
        } else if (consumeTime < dining.getLimitationTime()) {
            return dining.getAmount();
        } else {
            return dining.getLimitationAmount();
        }
    }

    /**
     * 根据用餐对象获取用餐时间段已用餐次数
     *
     * @param dining 用餐对象
     * @return 消费次数
     */
    private Integer countConsumeTime(String cardId, Dining dining) {
        LocalDateTime start = getLocalDateTime(dining.getStart());
        LocalDateTime end = getLocalDateTime(dining.getEnd());
        return countConsumeTime(cardId, start, end);
    }

    /**
     * 用指定的时分替换当前时间的时分
     *
     * @param time 时分
     * @return 替换之后的时间
     */
    private LocalDateTime getLocalDateTime(LocalTime time) {
        LocalDateTime now = LocalDateTime.now();
        return LocalDateTime.of(now.getYear(), now.getMonth(),
                now.getDayOfMonth(), time.getHour(), time.getMinute());
    }

    /**
     * 统计用餐时间段已用餐次数
     *
     * @param start 用餐开始时间
     * @param end   用餐结束时间
     * @return 消费次数
     */
    private Integer countConsumeTime(String cardId, LocalDateTime start, LocalDateTime end) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("card_id", cardId);
        queryWrapper.ge("create_time", start);
        queryWrapper.le("create_time", end);
        return consumeRecordMapper.selectCount(queryWrapper);
    }

}