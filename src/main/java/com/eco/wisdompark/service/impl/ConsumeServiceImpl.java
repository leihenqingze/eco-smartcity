package com.eco.wisdompark.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.eco.wisdompark.common.dto.ResponseData;
import com.eco.wisdompark.common.exceptions.WisdomParkException;
import com.eco.wisdompark.converter.req.ChangeAmountConverter;
import com.eco.wisdompark.converter.req.ConsumeRecordConverter;
import com.eco.wisdompark.converter.resp.ConsumeRespDtoConverter;
import com.eco.wisdompark.domain.dto.CalculateAmountDto;
import com.eco.wisdompark.domain.dto.req.consume.ConsumeDto;
import com.eco.wisdompark.domain.dto.resp.ConsumeRespDto;
import com.eco.wisdompark.domain.model.*;
import com.eco.wisdompark.enums.AmountChangeType;
import com.eco.wisdompark.enums.ConsumeType;
import com.eco.wisdompark.enums.DiningType;
import com.eco.wisdompark.mapper.*;
import com.eco.wisdompark.service.ConsumeService;
import com.eco.wisdompark.strategy.consume.ConsumeStrategy;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalTime;

/**
 * 消费服务类
 */
@Service
public class ConsumeServiceImpl implements ConsumeService {

    @Autowired
    private CpuCardMapper cpuCardMapper;
    @Autowired
    private ConsumeRecordMapper consumeRecordMapper;
    @Autowired
    private ChangeAmountMapper changeAmountMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private DeptMapper deptMapper;
    @Autowired
    private PosMapper posMapper;
    @Autowired
    private ConsumeStrategy consumeStrategy;
    @Autowired
    private ConsumeRespDtoConverter consumeRespDtoConverter;

    /**
     * 进行消费
     *
     * @param consumeDto 消费请求对象
     * @return 消费金额
     */
    @Transactional
    public ConsumeRespDto consume(ConsumeDto consumeDto) {
        Pos pos = getPosByPosNum(consumeDto.getPosNum());
        ConsumeType consumeType = ConsumeType.valueOf(pos.getPosConsumeType());
        CpuCard cpuCardBefore = getCpuCardByCardId(consumeDto.getCardId());
        User user = userMapper.selectById(cpuCardBefore.getUserId());
        Dept dept = deptMapper.selectById(user.getDeptId());
        Dept parent = deptMapper.selectById(dept.getDeptUpId());
        try {
            if (ConsumeType.SHOP.equals(consumeType)) {
                CpuCard consumeAfter = updateCpuCard(consumeDto.getAmount(),
                        pos, consumeType, null, cpuCardBefore);
                return consumeRespDtoConverter.shopSuccess(user, parent, dept, consumeAfter, consumeDto.getAmount());
            } else if (ConsumeType.DINING.equals(consumeType)) {
                DiningType diningType = DiningType.valueOf(LocalTime.now());
                CalculateAmountDto calculateAmountDto = consumeStrategy
                        .calculateAmount(cpuCardBefore.getCardId(), dept, diningType);
                CpuCard consumeAfter = updateCpuCard(calculateAmountDto.getAmount(), pos,
                        consumeType, diningType, cpuCardBefore);
                return consumeRespDtoConverter.diningSuccess(user, parent, dept,
                        consumeAfter, diningType, calculateAmountDto);
            }
        } catch (WisdomParkException e) {
            return consumeRespDtoConverter.convert(user, parent, dept, cpuCardBefore, e.getMessage());
        }
        return null;
    }

    /**
     * 更新用户卡中余额
     *
     * @param amount        消费金额
     * @param pos           POS机
     * @param consumeType   消费类型
     * @param diningType    用餐类型
     * @param cpuCardBefore 变动前CPU卡
     */
    private CpuCard updateCpuCard(BigDecimal amount, Pos pos, ConsumeType consumeType,
                                  DiningType diningType, CpuCard cpuCardBefore) {
        CpuCard consumeAfter = new CpuCard();
        BeanUtils.copyProperties(cpuCardBefore, consumeAfter);

        BigDecimal totalAmount = consumeAfter.getRechargeBalance().add(consumeAfter.getSubsidyBalance());
        if (totalAmount.compareTo(amount) == -1) {
            throw new WisdomParkException(ResponseData.STATUS_CODE_469, "卡内余额不足");
        } else {
            if (consumeAfter.getRechargeBalance().compareTo(amount) == -1) {
                BigDecimal rechargeAmount = consumeAfter.getRechargeBalance();
                BigDecimal subsidyAmount = totalAmount.subtract(amount);
                consumeAfter.setRechargeBalance(new BigDecimal(0));
                consumeAfter.setSubsidyBalance(subsidyAmount);
                cpuCardMapper.updateById(consumeAfter);
                saveConsumeRecord(rechargeAmount, cpuCardBefore.getSubsidyBalance().subtract(subsidyAmount),
                        pos, consumeType, consumeAfter, diningType);
                saveChangeAmount(amount, AmountChangeType.RECHARGE_AMOUNT, cpuCardBefore, consumeAfter);
                saveChangeAmount(amount, AmountChangeType.SUBSIDY_AMOUNT, cpuCardBefore, consumeAfter);
            } else {
                consumeAfter.setRechargeBalance(consumeAfter.getRechargeBalance().subtract(amount));
                saveConsumeRecord(amount, new BigDecimal(0), pos, consumeType, consumeAfter, diningType);
                saveChangeAmount(amount, AmountChangeType.RECHARGE_AMOUNT, cpuCardBefore, consumeAfter);
                cpuCardMapper.updateById(consumeAfter);
            }
        }
        return consumeAfter;
    }

    /**
     * 根据卡物理ID获取CPU卡
     *
     * @param cardId 卡物理ID
     * @return CPU卡
     */
    private CpuCard getCpuCardByCardId(String cardId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("card_id", cardId);
        return cpuCardMapper.selectOne(queryWrapper);
    }

    /**
     * 根据POS机编号查询POS机
     *
     * @param posNum POS机编号
     * @return POS机
     */
    private Pos getPosByPosNum(String posNum) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("pos_num", posNum);
        return posMapper.selectOne(queryWrapper);
    }

    /**
     * 保存消费记录
     *
     * @param rechargeAmount 消费的充值金额
     * @param subsidyAmount  消费的补助金额
     * @param pos            POS机
     * @param consumeType    消费类型
     * @param cpuCard        cpu卡
     * @param diningType     用餐类型
     */
    private void saveConsumeRecord(BigDecimal rechargeAmount,
                                   BigDecimal subsidyAmount,
                                   Pos pos, ConsumeType consumeType,
                                   CpuCard cpuCard, DiningType diningType) {
        ConsumeRecord consumeRecord = ConsumeRecordConverter.
                changeAmount(rechargeAmount, subsidyAmount,
                        pos, consumeType, cpuCard, diningType);
        consumeRecordMapper.insert(consumeRecord);
    }

    /**
     * 保存金额变动记录
     *
     * @param amount           变动金额
     * @param amountChangeType 金额变动类型
     * @param cpuCardBefore    消费前对象
     * @param cpuCardAfter     消费后对象
     */
    private void saveChangeAmount(BigDecimal amount, AmountChangeType amountChangeType,
                                  CpuCard cpuCardBefore, CpuCard cpuCardAfter) {
        ChangeAmount changeAmount = ChangeAmountConverter.
                changeAmount(amount, amountChangeType, cpuCardBefore, cpuCardAfter);
        changeAmountMapper.insert(changeAmount);
    }

}