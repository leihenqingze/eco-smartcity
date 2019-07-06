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
     * @param consumeAgo     消费前CPU卡
     * @param consumeAfter   消费前后CPU卡
     * @param diningType     用餐类型
     * @return 消费记录
     */
    public static ConsumeRecord changeAmount(BigDecimal rechargeAmount,
                                             BigDecimal subsidyAmount,
                                             Pos pos, ConsumeType consumeType,
                                             CpuCard consumeAgo,
                                             CpuCard consumeAfter, DiningType diningType) {
        ConsumeRecord consumeRecord = new ConsumeRecord();
        consumeRecord.setCardId(consumeAfter.getCardId());
        consumeRecord.setCardSerialNo(consumeAfter.getCardSerialNo());
        consumeRecord.setUserId(consumeAfter.getUserId());
        consumeRecord.setRechargeAmount(rechargeAmount);
        consumeRecord.setSubsidyAmount(subsidyAmount);
        consumeRecord.setPosNum(pos.getPosNum());
        consumeRecord.setType(consumeType.getCode());
        consumeRecord.setRechargeAgoAmount(consumeAgo.getRechargeBalance());
        consumeRecord.setRechargeAfterAmount(consumeAfter.getRechargeBalance());
        consumeRecord.setSubsidyAgoAmount(consumeAgo.getSubsidyBalance());
        consumeRecord.setSubsidyAfterAmount(consumeAfter.getSubsidyBalance());

        if (Objects.nonNull(diningType))
            consumeRecord.setDiningType(diningType.getCode());
        return consumeRecord;
    }

}