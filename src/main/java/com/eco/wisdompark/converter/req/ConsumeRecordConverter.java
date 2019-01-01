package com.eco.wisdompark.converter.req;

import com.eco.wisdompark.domain.model.ConsumeRecord;
import com.eco.wisdompark.domain.model.CpuCard;
import com.eco.wisdompark.domain.model.Pos;
import com.eco.wisdompark.enums.ConsumeType;
import com.eco.wisdompark.enums.DiningType;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * 消费记录转换类
 */
public class ConsumeRecordConverter {

    /**
     * 消费记录转换类
     *
     * @param rechargeAmount 充值消费金额
     * @param subsidyAmount  补助消费金额
     * @param pos            POS机
     * @param consumeType    消费类型
     * @param cpuCard        CPU卡
     * @param diningType     用餐类型
     * @return 消费记录
     */
    public static ConsumeRecord changeAmount(BigDecimal rechargeAmount,
                                             BigDecimal subsidyAmount,
                                             Pos pos, ConsumeType consumeType,
                                             CpuCard cpuCard, DiningType diningType) {
        ConsumeRecord consumeRecord = new ConsumeRecord();
        consumeRecord.setCardId(cpuCard.getCardId());
        consumeRecord.setCardSerialNo(cpuCard.getCardSerialNo());
        consumeRecord.setUserId(cpuCard.getUserId());
        consumeRecord.setRechargeAmount(rechargeAmount);
        consumeRecord.setSubsidyAmount(subsidyAmount);
        consumeRecord.setPosNum(pos.getPosNum());
        consumeRecord.setType(consumeType.getCode());
        if (Objects.nonNull(diningType))
            consumeRecord.setDiningType(diningType.getCode());
        return consumeRecord;
    }

}
