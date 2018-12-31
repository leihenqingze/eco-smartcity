package com.eco.wisdompark.converter.req;

import com.eco.wisdompark.domain.model.CpuCard;
import com.eco.wisdompark.enums.CardSource;
import com.eco.wisdompark.enums.CardType;
import com.eco.wisdompark.enums.ReportLossStstus;
import com.eco.wisdompark.enums.YesNo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * CPU卡类型构造转换
 */
public class CpuCardConverter {


    /**
     * cpuCard 类构造
     * @param userId
     * @param cardId
     * @param cardSerialNo
     * @param cardType
     * @param cardSource
     * @param deposit
     * @param rechargeBalance
     * @param subsidyBalance
     * @return
     */
    public static CpuCard create(Integer userId, String cardId, String cardSerialNo,
                                 CardType cardType, CardSource cardSource, BigDecimal deposit,
                                 BigDecimal rechargeBalance, BigDecimal subsidyBalance){
        CpuCard cpuCard = new CpuCard();
        cpuCard.setUserId(userId);
        cpuCard.setCardId(cardId);
        cpuCard.setCardSerialno(cardSerialNo);
        cpuCard.setCardType(cardType.getCode());
        cpuCard.setCardSource(cardSource.getCode());
        cpuCard.setCreateTime(LocalDateTime.now());
        cpuCard.setDeposit(deposit);
        cpuCard.setRechargeBalance(rechargeBalance);
        cpuCard.setSubsidyBalance(subsidyBalance);
        cpuCard.setDel(YesNo.NO.getCode());
        cpuCard.setReportLossStstus(ReportLossStstus.IN_USE.getCode());
        return cpuCard;
    }

}
