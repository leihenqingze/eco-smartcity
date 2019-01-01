package com.eco.wisdompark.converter.resp;

import com.eco.wisdompark.domain.dto.resp.RespMakingCpuCardDto;
import com.eco.wisdompark.domain.model.CpuCard;
import com.eco.wisdompark.domain.model.User;

/**
 * 制卡返回类Dto 构造
 */
public class RespMakingCpuCardDtoConverter {


    /**
     * 制卡Dto 构造
     * @param cpuCard
     * @param user
     * @return
     */
    public static RespMakingCpuCardDto create(CpuCard cpuCard, User user){
        RespMakingCpuCardDto respMakingCpuCardDto = new RespMakingCpuCardDto();
        respMakingCpuCardDto.setId(cpuCard.getId());
        respMakingCpuCardDto.setUserName(user.getUserName());
        respMakingCpuCardDto.setUserCardNum(user.getUserCardNum());
        respMakingCpuCardDto.setPhoneNum(user.getPhoneNum());
        respMakingCpuCardDto.setDeptId(user.getDeptId());
        respMakingCpuCardDto.setDeposit(cpuCard.getDeposit());
        return respMakingCpuCardDto;
    }

}
