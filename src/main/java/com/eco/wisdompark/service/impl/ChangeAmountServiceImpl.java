package com.eco.wisdompark.service.impl;

import com.eco.wisdompark.converter.req.ChangeAmountConverter;
import com.eco.wisdompark.domain.dto.inner.InnerCpuCardInfoDto;
import com.eco.wisdompark.domain.model.ChangeAmount;
import com.eco.wisdompark.domain.model.CpuCard;
import com.eco.wisdompark.enums.AmountChangeType;
import com.eco.wisdompark.mapper.ChangeAmountMapper;
import com.eco.wisdompark.service.ChangeAmountService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 金额变动记录 服务实现类
 * </p>
 *
 * @author litao
 * @since 2018-12-28
 */
@Service
public class ChangeAmountServiceImpl extends ServiceImpl<ChangeAmountMapper, ChangeAmount> implements ChangeAmountService {

    /**
     * 保存 充值 金额变更记录
     * @param cardInfoDto
     * @param changeAmt
     * @param changeType
     * @return
     */
    @Override
    public boolean saveRechargeChanageAmountRecord(InnerCpuCardInfoDto cardInfoDto, BigDecimal changeAmt, AmountChangeType changeType) {
        CpuCard oldCpuCard = new CpuCard();
        oldCpuCard.setCardId(cardInfoDto.getCardId());
        oldCpuCard.setCardSerialno(cardInfoDto.getCardSerialNo());
        oldCpuCard.setUserId(cardInfoDto.getUserId());
        oldCpuCard.setRechargeBalance(cardInfoDto.getRechargeBalance());
        oldCpuCard.setSubsidyBalance(cardInfoDto.getSubsidyBalance());

        CpuCard newCpuCard = new CpuCard();
        newCpuCard.setRechargeBalance(cardInfoDto.getRechargeBalance().add(changeAmt));
        newCpuCard.setSubsidyBalance(cardInfoDto.getSubsidyBalance());
        ChangeAmount changeAmount = ChangeAmountConverter.changeAmount(changeAmt,AmountChangeType.TOP_UP, oldCpuCard, newCpuCard);
        return save(changeAmount);
    }
}
