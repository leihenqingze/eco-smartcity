package com.eco.wisdompark.converter.req;

import com.eco.wisdompark.domain.model.ChangeAmount;
import com.eco.wisdompark.domain.model.CpuCard;
import com.eco.wisdompark.enums.AmountChangeType;

import java.math.BigDecimal;

/**
 * 金额变动记录转换类
 */
public class ChangeAmountConverter {

    /**
     * 金额变动转换方法
     *
     * @param amount           变动金额
     * @param amountChangeType 金额变动类型
     * @param cpuCardBefore    变动前CPU卡
     * @param cpuCardAfter     变动后CPU卡
     * @return
     */
    public static ChangeAmount changeAmount(BigDecimal amount, AmountChangeType amountChangeType,
                                            CpuCard cpuCardBefore, CpuCard cpuCardAfter) {
        ChangeAmount changeAmount = new ChangeAmount();
        changeAmount.setCardId(cpuCardBefore.getCardId());
        changeAmount.setCardSerialno(cpuCardBefore.getCardSerialno());
        changeAmount.setUserId(cpuCardBefore.getUserId());
        changeAmount.setChangeAmount(amount);

        changeAmount.setChangeAgoRecharge(cpuCardBefore.getRechargeBalance());
        changeAmount.setChangeAfterRecharge(cpuCardAfter.getRechargeBalance());

        changeAmount.setChangeAgoSubsidy(cpuCardBefore.getSubsidyBalance());
        changeAmount.setChangeAfterSubsidy(cpuCardAfter.getSubsidyBalance());
        changeAmount.setChangeType(amountChangeType.getCode());
        return changeAmount;
    }

}
