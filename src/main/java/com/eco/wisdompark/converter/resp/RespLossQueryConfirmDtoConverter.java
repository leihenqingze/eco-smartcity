package com.eco.wisdompark.converter.resp;

import com.eco.wisdompark.domain.dto.resp.RespLossQueryConfirmDto;
import com.eco.wisdompark.domain.model.CpuCard;
import com.eco.wisdompark.domain.model.User;

import java.math.BigDecimal;

/**
 * 补卡查询确认返回类 构造
 */
public class RespLossQueryConfirmDtoConverter {

    /**
     * 补卡查询信息构造
     * @param cpuCard
     * @param user
     * @return
     */
    public static RespLossQueryConfirmDto create(CpuCard cpuCard, User user){
        RespLossQueryConfirmDto respLossQueryConfirmDto = new RespLossQueryConfirmDto();
        respLossQueryConfirmDto.setUserName(user.getUserName());
        respLossQueryConfirmDto.setDeptId(user.getDeptId());
        respLossQueryConfirmDto.setPhoneNum(user.getPhoneNum());
        respLossQueryConfirmDto.setUserCardNum(user.getUserCardNum());
        respLossQueryConfirmDto.setOldCardId(cpuCard.getCardId());
        BigDecimal rechargeBalance = cpuCard.getRechargeBalance() != null ? cpuCard.getRechargeBalance() : new BigDecimal(0);
        BigDecimal subsidyBalance = cpuCard.getSubsidyBalance() != null ? cpuCard.getSubsidyBalance() : new BigDecimal(0);
        respLossQueryConfirmDto.setRechargeBalance(rechargeBalance);
        respLossQueryConfirmDto.setSubsidyBalance(subsidyBalance);
        respLossQueryConfirmDto.setBalance(rechargeBalance.add(subsidyBalance));
        respLossQueryConfirmDto.setUserId(user.getId());
        return respLossQueryConfirmDto;
    }

}
