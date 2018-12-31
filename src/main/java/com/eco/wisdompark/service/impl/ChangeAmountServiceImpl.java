package com.eco.wisdompark.service.impl;

import com.eco.wisdompark.common.dto.ResponseData;
import com.eco.wisdompark.common.exceptions.WisdomParkException;
import com.eco.wisdompark.domain.dto.CpuCardInfoDto;
import com.eco.wisdompark.domain.model.ChangeAmount;
import com.eco.wisdompark.enums.AmountChangeType;
import com.eco.wisdompark.enums.ChangeType;
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


    @Override
    public boolean saveChanageAmountRecord(CpuCardInfoDto cardInfoDto, BigDecimal changeAmt, AmountChangeType changeType) {

        // 2.保存变更金额
        ChangeAmount changeAmount = new ChangeAmount();
        changeAmount.setCardId(cardInfoDto.getCardId());
        changeAmount.setCardSerialno(cardInfoDto.getCardSerialNo());
        changeAmount.setUserId(cardInfoDto.getUserId());
        changeAmount.setChangeType(changeType.getCode());
        changeAmount.setCreateTime(LocalDateTime.now());
        changeAmount.setChangeAgoRecharge(cardInfoDto.getRechargeBalance());
        changeAmount.setChangeAgoSubsidy(cardInfoDto.getSubsidyBalance());
        if (changeType == AmountChangeType.TOP_UP){
            // 充值操作（余额 + changeAmt）
            changeAmount.setChangeAfterRecharge(cardInfoDto.getRechargeBalance().add(changeAmt));
            changeAmount.setChangeAfterSubsidy(cardInfoDto.getSubsidyBalance());
        }else if (changeType == AmountChangeType.RECHARGE_AMOUNT){
            // 消费充值余额（余额 - changeAmt）
//            if (cardInfoDto.getRechargeBalance().compareTo(changeAmt) ==  -1){
//                throw new WisdomParkException(ResponseData.STATUS_CODE_600, "user already exist");
//            }
            changeAmount.setChangeAfterRecharge(cardInfoDto.getRechargeBalance().subtract(changeAmt));
            changeAmount.setChangeAfterSubsidy(cardInfoDto.getSubsidyBalance());
        }else if (changeType == AmountChangeType.SUBSIDY_AMOUNT){
            // 消费补助余额（补助余额 - changeAmt）
            changeAmount.setChangeAfterSubsidy(cardInfoDto.getSubsidyBalance().subtract(changeAmt));
            changeAmount.setChangeAfterRecharge(cardInfoDto.getRechargeBalance());
        }
        return save(changeAmount);
    }
}
