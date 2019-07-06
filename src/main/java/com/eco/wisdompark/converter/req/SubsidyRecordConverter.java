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
     * @param cpuCardBefore 变动前CPU卡
     * @param cpuCardAfter  变动后CPU卡
     * @param subsidyType   补助类型
     * @return 补助记录
     */
    public static SubsidyRecord converter(BigDecimal subsidyAmount,
                                          CpuCard cpuCardBefore, CpuCard cpuCardAfter, SubsidyType subsidyType) {
        SubsidyRecord record = new SubsidyRecord();
        record.setAmount(subsidyAmount);
        record.setCardId(cpuCardAfter.getCardId());
        record.setCardSerialNo(cpuCardAfter.getCardSerialNo());
        record.setUserId(cpuCardAfter.getUserId());
        record.setType(subsidyType.getCode());
        record.setSubsidyAgoAmount(cpuCardBefore.getSubsidyBalance());
        record.setSubsidyAfterAmount(cpuCardAfter.getSubsidyBalance());
        return record;
    }

}