package com.eco.wisdompark.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.eco.wisdompark.common.dto.ResponseData;
import com.eco.wisdompark.common.exceptions.WisdomParkException;
import com.eco.wisdompark.common.utils.StringTools;
import com.eco.wisdompark.converter.req.ChangeAmountConverter;
import com.eco.wisdompark.domain.model.ChangeAmount;
import com.eco.wisdompark.domain.model.CpuCard;
import com.eco.wisdompark.enums.AmountChangeType;
import com.eco.wisdompark.enums.ReportLossStstus;
import com.eco.wisdompark.mapper.ChangeAmountMapper;
import com.eco.wisdompark.mapper.CpuCardMapper;
import com.eco.wisdompark.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * <p>
 * 退卡 服务实现类
 * </p>
 *
 * @author litao
 * @since 2019-01-06
 */
@Slf4j
@Service
public class ReturnCardServiceImpl implements ReturnCardService {

    @Autowired
    private CpuCardMapper cpuCardMapper;
    @Autowired
    private ChangeAmountMapper changeAmountMapper;

    @Transactional
    @Override
    public boolean returnCard(String cpuCardId) {
        CpuCard changeBefore = queryCardInfoByCardId(StringTools.cardDecimalToHexString(cpuCardId));
        if (changeBefore == null) {
            throw new WisdomParkException(ResponseData.STATUS_CODE_601, "该卡不能进行退卡操作");
        }
        CpuCard changeAfter = new CpuCard();
        BeanUtils.copyProperties(changeBefore, changeAfter);
        changeAfter.setRechargeBalance(new BigDecimal(0));
        changeAfter.setDeposit(new BigDecimal(0));
        changeAfter.setReportLossStstus(ReportLossStstus.RETURN_CARD.getCode());
        ChangeAmount changeAmount = ChangeAmountConverter.
                changeAmount(changeBefore.getRechargeBalance(),
                        AmountChangeType.RETURN_CARD, changeBefore, changeAfter);
        cpuCardMapper.updateById(changeAfter);
        changeAmountMapper.insert(changeAmount);
        return true;
    }

    /**
     * 查询可用的CPU卡
     *
     * @param cardId 卡ID
     * @return CPU卡信息
     */
    private CpuCard queryCardInfoByCardId(String cardId) {
        QueryWrapper<CpuCard> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("card_id", cardId);
        queryWrapper.eq("report_loss_ststus", ReportLossStstus.IN_USE);
        return cpuCardMapper.selectOne(queryWrapper);
    }

}
