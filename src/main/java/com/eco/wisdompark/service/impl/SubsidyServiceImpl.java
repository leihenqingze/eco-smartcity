package com.eco.wisdompark.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.eco.wisdompark.converter.req.ChangeAmountConverter;
import com.eco.wisdompark.converter.req.SubsidyRecordConverter;
import com.eco.wisdompark.domain.dto.req.subsidy.ManualSubsidyDto;
import com.eco.wisdompark.domain.model.*;
import com.eco.wisdompark.enums.AmountChangeType;
import com.eco.wisdompark.enums.ReportLossStstus;
import com.eco.wisdompark.enums.SubsidyStatus;
import com.eco.wisdompark.enums.SubsidyType;
import com.eco.wisdompark.mapper.*;
import com.eco.wisdompark.service.SubsidyService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.javatuples.Triplet;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 补助服务类
 */
@Slf4j
@Service
public class SubsidyServiceImpl implements SubsidyService {

    @Autowired
    private CpuCardMapper cpuCardMapper;
    @Autowired
    private SubsidyRecordMapper subsidyRecordMapper;
    @Autowired
    private ChangeAmountMapper changeAmountMapper;
    @Autowired
    private SubsidyRuleMapper subsidyRuleMapper;
    @Autowired
    private UserMapper userMapper;

    @Transactional
    @Override
    public void manualSubsidy(ManualSubsidyDto manualSubsidyDto) {
        CpuCard changeBefore = getCpuCardByUserId(manualSubsidyDto.getUserId());
        CpuCard changeAfter = new CpuCard();
        BeanUtils.copyProperties(changeBefore, changeAfter);
        changeAfter.setSubsidyBalance(changeAfter.getSubsidyBalance().add(manualSubsidyDto.getSubsidyAmount()));
        cpuCardMapper.updateById(changeAfter);
        saveSubsidyRecord(manualSubsidyDto.getSubsidyAmount(), changeAfter, SubsidyType.MANUAL);
        saveChangeAmount(manualSubsidyDto.getSubsidyAmount(), changeBefore, changeAfter);
    }

    @Override
    @Scheduled(cron = "0 0 1 * * ?")
//    @Scheduled(cron = "0 */1 * * * ?")
    public void automaticSubsidy() {
        List<SubsidyRule> subsidyRules = selectRevSubsidyRuleByNow();
        subsidyRules.forEach(subsidyRule -> {
            List<User> users = selectUsersByDeptId(subsidyRule.getDeptId());
            List<Integer> userIds = users.stream()
                    .map(user -> user.getId())
                    .collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(userIds)) {
                List<CpuCard> cpuCards = selectCpuCardsByUserIds(userIds);
                Triplet<List<CpuCard>, List<SubsidyRecord>, List<ChangeAmount>>
                        triplet = buildSubsidy(subsidyRule, cpuCards);
                try {
                    saveAutomaticSubsidy(triplet);
                } catch (Exception ex) {
                    log.error("subsidyRules:" + subsidyRule.getId() + ",error", ex);
                }
            }
        });
    }

    /**
     * 保存自动补助信息
     *
     * @param triplet 补助信息
     */
    @Transactional
    public void saveAutomaticSubsidy(Triplet<List<CpuCard>, List<SubsidyRecord>, List<ChangeAmount>> triplet) {
        if (CollectionUtils.isNotEmpty(triplet.getValue0())) {
            cpuCardMapper.updateBatchSubsidy(triplet.getValue0());
            subsidyRecordMapper.insertBatch(triplet.getValue1());
            changeAmountMapper.insertBatch(triplet.getValue2());
        }
    }

    /**
     * 根据人员ID获取CPU卡
     *
     * @param userId 人员ID
     * @return CPU卡
     */
    private CpuCard getCpuCardByUserId(Integer userId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id", userId);
        queryWrapper.eq("report_loss_ststus", ReportLossStstus.IN_USE.getCode());
        return cpuCardMapper.selectOne(queryWrapper);
    }

    /**
     * 保存补助记录
     *
     * @param amount      补助金额
     * @param cpuCard     CPU卡
     * @param subsidyType 补助类型
     */
    private void saveSubsidyRecord(BigDecimal amount, CpuCard cpuCard, SubsidyType subsidyType) {
        SubsidyRecord subsidyRecord = SubsidyRecordConverter.converter(amount, cpuCard, subsidyType);
        subsidyRecordMapper.insert(subsidyRecord);
    }

    /**
     * 保存金额变动记录
     *
     * @param amount       变动金额
     * @param changeBefore 改变前对象
     * @param changeAfter  改变后对象
     */
    private void saveChangeAmount(BigDecimal amount, CpuCard changeBefore, CpuCard changeAfter) {
        ChangeAmount changeAmount = ChangeAmountConverter.
                changeAmount(amount, AmountChangeType.SUBSIDIES, changeBefore, changeAfter);
        changeAmountMapper.insert(changeAmount);
    }

    /**
     * 根据人员ID获取CPU卡
     *
     * @return 补助规则
     */
    private List<SubsidyRule> selectRevSubsidyRuleByNow() {
        LocalDate localDate = LocalDate.now();
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("subsidy_time", localDate.getDayOfMonth());
        queryWrapper.eq("subsidy_status", SubsidyStatus.START.getCode());
        return subsidyRuleMapper.selectList(queryWrapper);
    }

    /**
     * 根据人员ID获取CPU卡
     *
     * @return 补助规则
     */
    private List<User> selectUsersByDeptId(Integer deptId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("dept_id", deptId);
        return userMapper.selectList(queryWrapper);
    }

    /**
     * 根据人员ID获取CPU卡
     *
     * @return 补助规则
     */
    private List<CpuCard> selectCpuCardsByUserIds(List<Integer> userIds) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.in("user_id", userIds);
        return cpuCardMapper.selectList(queryWrapper);
    }

    /**
     * 构建补助信息
     *
     * @param subsidyRule 自动补助规则
     * @param cpuCards    CPU卡
     * @return 补助信息
     */
    private Triplet<List<CpuCard>, List<SubsidyRecord>, List<ChangeAmount>>
    buildSubsidy(SubsidyRule subsidyRule, List<CpuCard> cpuCards) {
        List<CpuCard> changeAfters = Lists.newArrayList();
        List<SubsidyRecord> subsidyRecords = Lists.newArrayList();
        List<ChangeAmount> changeAmounts = Lists.newArrayList();
        cpuCards.forEach(cpuCard -> {
            CpuCard changeAfter = new CpuCard();
            BeanUtils.copyProperties(cpuCard, changeAfter);
            changeAfter.setSubsidyBalance(changeAfter.getSubsidyBalance()
                    .add(subsidyRule.getSubsidyAmount()));
            changeAfters.add(changeAfter);
            SubsidyRecord subsidyRecord = SubsidyRecordConverter.converter
                    (subsidyRule.getSubsidyAmount(), cpuCard, SubsidyType.AUTOMATIC);
            subsidyRecords.add(subsidyRecord);
            ChangeAmount changeAmount = ChangeAmountConverter.
                    changeAmount(subsidyRule.getSubsidyAmount(),
                            AmountChangeType.SUBSIDIES, cpuCard, changeAfter);
            changeAmounts.add(changeAmount);
        });
        return new Triplet<>(changeAfters, subsidyRecords, changeAmounts);
    }

}