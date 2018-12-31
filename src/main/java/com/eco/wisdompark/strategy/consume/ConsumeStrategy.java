package com.eco.wisdompark.strategy.consume;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
public class ConsumeStrategy {

    @Autowired
    private ConsumeRecordMapper consumeRecordMapper;

    @Autowired
    private ConsumeTbStaffStrategy consumeTbStaffStrategy;

    @Autowired
    private ConsumeUnTbStaffStrategy consumeUnTbStaffStrategy;

    @Autowired
    private ConsumeGdStrategy consumeGdStrategy;

    @Autowired
    private ConsumePacStrategy consumePacStrategy;

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
        BigDecimal amount = null;
        Integer consumeTime = null;
        if (ConsumeIdentity.TB_STAFF.equals(consumeIdentity)) {
            consumeTime = countConsumeTime(cardId, diningType);
            amount = consumeTbStaffStrategy.consume(diningType, consumeTime);
        } else if (ConsumeIdentity.UN_TB_STAFF.equals(consumeIdentity)) {
            consumeTime = countConsumeTime(cardId, diningType);
            amount = consumeUnTbStaffStrategy.consume(diningType, consumeTime);
        } else if (ConsumeIdentity.GD.equals(consumeIdentity)) {
            LocalDateTime start = getLocalDateTime(DiningType.LUNCH.getStart());
            LocalDateTime end = getLocalDateTime(DiningType.DINNER.getEnd());
            consumeTime = countConsumeTime(cardId, start, end);
            amount = consumeGdStrategy.consume(diningType, consumeTime);
        } else if (ConsumeIdentity.PAC.equals(consumeIdentity)) {
            consumeTime = countConsumeTime(cardId, diningType);
            amount = consumePacStrategy.consume(diningType, consumeTime);
        }
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
     * @return 用餐费用
     */
    public BigDecimal calculateAmount(Dept dept, DiningType diningType, int consumeTime) throws WisdomParkException {
        ConsumeIdentity consumeIdentity = ConsumeIdentity.valueOf(dept.getConsumeIdentity());
        if (ConsumeIdentity.TB_STAFF.equals(consumeIdentity)) {
            return consumeTbStaffStrategy.consume(diningType, consumeTime);
        } else if (ConsumeIdentity.UN_TB_STAFF.equals(consumeIdentity)) {
            return consumeUnTbStaffStrategy.consume(diningType, consumeTime);
        } else if (ConsumeIdentity.GD.equals(consumeIdentity)) {
            return consumeGdStrategy.consume(diningType, consumeTime);
        } else if (ConsumeIdentity.PAC.equals(consumeIdentity)) {
            return consumePacStrategy.consume(diningType, consumeTime);
        }
        return null;
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
     * 统计用餐时的消费次数
     *
     * @param diningType 用餐类型
     * @return 消费次数
     */
    private Integer countConsumeTime(String cardId, DiningType diningType) {
        LocalDateTime start = getLocalDateTime(diningType.getStart());
        LocalDateTime end = getLocalDateTime(diningType.getEnd());
        return countConsumeTime(cardId, start, end);
    }

    /**
     * 统计用餐时的消费次数
     *
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