package com.eco.wisdompark.converter.req;

import com.eco.wisdompark.domain.model.CpuCard;
import com.eco.wisdompark.domain.model.SubsidyRecord;
import com.eco.wisdompark.enums.SubsidyType;

import java.math.BigDecimal;

/**
 * 补助记录转换类
 */
public class SubsidyRecordConverter {

    /**
     * 补助记录转换类
     *
     * @param subsidyAmount 补助消费金额
     * @param cpuCard       CPU卡
     * @param subsidyType   补助类型
     * @return 补助记录
     */
    public static SubsidyRecord converter(BigDecimal subsidyAmount,
                                          CpuCard cpuCard, SubsidyType subsidyType) {
        SubsidyRecord record = new SubsidyRecord();
        record.setAmount(subsidyAmount);
        record.setCardId(cpuCard.getCardId());
        record.setCardSerialno(cpuCard.getCardId());
        record.setUserId(cpuCard.getUserId());
        record.setType(subsidyType.getCode());
        return record;
    }

}