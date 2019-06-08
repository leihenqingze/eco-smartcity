package com.eco.wisdompark.converter.resp;

import com.eco.wisdompark.domain.dto.CalculateAmountDto;
import com.eco.wisdompark.domain.dto.resp.ConsumeServiceRespDto;
import com.eco.wisdompark.domain.model.CpuCard;
import com.eco.wisdompark.domain.model.Dept;
import com.eco.wisdompark.domain.model.User;
import com.eco.wisdompark.enums.DiningType;
import com.eco.wisdompark.strategy.consume.ConsumeCalculate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * 消费返回转换类
 */
@Component
public class ConsumeRespDtoConverter {

    @Autowired
    private ConsumeCalculate consumeStrategy;

    /**
     * 用餐正常返回
     *
     * @param user               人员
     * @param parent             上级部门
     * @param dept               当前部门
     * @param cpuCardAfter       消费之后的CPU卡信息
     * @param diningType         用餐类型
     * @param calculateAmountDto 计算出的用餐费用和用餐次数
     * @return 用餐返回信息
     */
    public ConsumeServiceRespDto diningConvert(User user, Dept parent, Dept dept, CpuCard cpuCardAfter,
                                               DiningType diningType, CalculateAmountDto calculateAmountDto) {
        ConsumeServiceRespDto changeAmount = convert(user, parent, dept, cpuCardAfter,
                calculateAmountDto.getAmount().toString(), null);
        try {
            BigDecimal nextConsume = consumeStrategy.calculateAmount(dept, diningType,
                    calculateAmountDto.getConsumeTime() + 1);
            changeAmount.setNextConsume(nextConsume.toString());
        } catch (Exception ex) {
            changeAmount.setNextConsume(ex.getMessage());
        }
        return changeAmount;
    }

    /**
     * 购物正常返回
     *
     * @param user         人员
     * @param parent       上级部门
     * @param dept         当前部门
     * @param cpuCardAfter 消费之后的CPU卡信息
     * @param amount       消费金额
     * @return 用餐返回信息
     */
    public ConsumeServiceRespDto shopConvert(User user, Dept parent, Dept dept, CpuCard cpuCardAfter,
                                             BigDecimal amount) {
        return convert(user, parent, dept, cpuCardAfter, amount.toString(), null);
    }

    /**
     * 消费返回
     *
     * @param user         人员
     * @param parent       上级部门
     * @param dept         当前部门
     * @param cpuCardAfter 消费之后的CPU卡信息
     * @param amount       消费金额
     * @return 用餐返回信息
     */
    public ConsumeServiceRespDto convert(User user, Dept parent, Dept dept, CpuCard cpuCardAfter,
                                         String amount, Integer errorCode) {
        ConsumeServiceRespDto changeAmount = new ConsumeServiceRespDto();
        changeAmount.setUserName(user.getUserName());
        changeAmount.setDeptName(parent.getDeptName() + "-" + dept.getDeptName());
        changeAmount.setAmount(amount);
        BigDecimal balance = cpuCardAfter.getRechargeBalance().add(cpuCardAfter.getSubsidyBalance());
        changeAmount.setBalance(balance.toString());
        changeAmount.setErrorCode(errorCode);
        return changeAmount;
    }

}
