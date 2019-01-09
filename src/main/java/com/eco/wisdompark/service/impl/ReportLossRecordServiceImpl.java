package com.eco.wisdompark.service.impl;

import com.eco.wisdompark.common.dto.ResponseData;
import com.eco.wisdompark.common.exceptions.WisdomParkException;
import com.eco.wisdompark.common.utils.StringTools;
import com.eco.wisdompark.converter.req.CpuCardConverter;
import com.eco.wisdompark.converter.req.ReportLossRecordConverter;
import com.eco.wisdompark.converter.resp.RespReissueCardDtoConverter;
import com.eco.wisdompark.domain.dto.req.card.ReissueCardDto;
import com.eco.wisdompark.domain.dto.resp.RespReissueCardDto;
import com.eco.wisdompark.domain.model.CpuCard;
import com.eco.wisdompark.domain.model.ReportLossRecord;
import com.eco.wisdompark.domain.model.User;
import com.eco.wisdompark.enums.CardSource;
import com.eco.wisdompark.enums.CardType;
import com.eco.wisdompark.enums.ReportLossStstus;
import com.eco.wisdompark.mapper.ReportLossRecordMapper;
import com.eco.wisdompark.service.CpuCardService;
import com.eco.wisdompark.service.ReportLossRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eco.wisdompark.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 挂失记录 服务实现类
 * </p>
 *
 * @author litao
 * @since 2018-12-28
 */
@Slf4j
@Service
public class ReportLossRecordServiceImpl extends ServiceImpl<ReportLossRecordMapper, ReportLossRecord> implements ReportLossRecordService {

    /**
     * 默认押金20元
     */
    private static final int defaultDepositAmt = 20;

    @Autowired
    private CpuCardService cpuCardService;

    @Autowired
    private UserService userService;

    @Override
    @Transactional
    public boolean reissueCard(ReissueCardDto reissueCardDto) {
        // 1.查询原CPU卡信息
        int userId = reissueCardDto.getUserId();
        List<Integer> userIds = new ArrayList<>();
        userIds.add(userId);
        List<CpuCard> cpuCardList = cpuCardService.getCpuCardByUserIds(userIds);
        if (CollectionUtils.isEmpty(cpuCardList)){
            log.error("reissueCard not query user or old cpuCard not exist... userId:{}", reissueCardDto.getUserId());
            throw new WisdomParkException(ResponseData.STATUS_CODE_601, "用户或卡信息不存在");
        }

        // 2.查询用户信息
        User user = userService.queryByUserId(userId);
        if (user == null){
            log.error("reissueCard user not exists... userId:{}", userId);
            throw new WisdomParkException(ResponseData.STATUS_CODE_601, "user or cpuCard not exist");
        }

        String newCardId = StringTools.cardDecimalToHexString(reissueCardDto.getCardId());
        String newCardSerialNo = reissueCardDto.getCardSerialNo();
        CpuCard cpuCard = cpuCardList.get(0);
        String oldCardId = cpuCard.getCardId();
        String oldCardSerialNo = cpuCard.getCardSerialNo();
        BigDecimal rechargeBalance = cpuCard.getRechargeBalance() != null ? cpuCard.getRechargeBalance() : new BigDecimal(0);
        BigDecimal subsidyBalance = cpuCard.getSubsidyBalance() != null ? cpuCard.getSubsidyBalance() : new BigDecimal(0);

        // 3.将原卡信息置为挂失状态
        cpuCard.setReportLossStstus(ReportLossStstus.REPORT_LOSS.getCode());
        cpuCardService.updateCpuCard(cpuCard);

        // 4.校验新CPU卡ID是否存在
        boolean isExist = cpuCardService.queryCardInfoIsExist(newCardId, newCardSerialNo);
        if (isExist){
            log.error("reissueCard newCardId:{} or newCardSerialNo:{} exists...", newCardId, newCardSerialNo);
            throw new WisdomParkException(ResponseData.STATUS_CODE_602, "新CPU卡ID或卡序列号已被使用");
        }

        // 5.插入新卡信息
        BigDecimal deposit = getDeposit(reissueCardDto.getDeposit());
        CpuCard newCpuCard =  CpuCardConverter.create(userId, newCardId, newCardSerialNo,
                CardType.CPU, CardSource.MAKE_CARD, deposit, rechargeBalance, subsidyBalance);
        cpuCardService.save(newCpuCard);

        // 6.插入挂失记录
        ReportLossRecord reportLossRecord = ReportLossRecordConverter.create(userId, oldCardId, newCardId, newCardSerialNo, oldCardSerialNo);
        baseMapper.insert(reportLossRecord);
        return true;
    }


    /**
     * 获取押金
     * @param paramDeposit
     * @return
     */
    private BigDecimal getDeposit(BigDecimal paramDeposit){
        BigDecimal deposit = null;
        if (paramDeposit == null || paramDeposit.doubleValue() <= 0){
            deposit = new BigDecimal(defaultDepositAmt);
        }else {
            deposit =  paramDeposit;
        }
        return deposit;
    }


    public static void main(String[] args) {
        BigDecimal  a = new BigDecimal(1);
        BigDecimal b = null;
        System.out.println("1111" + a.add(b));
    }

}
