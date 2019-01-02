package com.eco.wisdompark.converter.resp;

import com.eco.wisdompark.domain.dto.resp.RespActiveCpuCardDto;
import com.eco.wisdompark.domain.model.CpuCard;
import com.eco.wisdompark.domain.model.User;

/**
 * 卡片激活成功返回类Dto 构造
 */
public class RespActiveCpuCardDtoConverter {

    public static RespActiveCpuCardDto create(CpuCard cpuCard, User user){
        RespActiveCpuCardDto respActiveCpuCardDto = new RespActiveCpuCardDto();
        respActiveCpuCardDto.setId(cpuCard.getId());
        respActiveCpuCardDto.setUserName(user.getUserName());
        respActiveCpuCardDto.setUserCardNum(user.getUserCardNum());
        respActiveCpuCardDto.setPhoneNum(user.getPhoneNum());
        respActiveCpuCardDto.setDeptId(user.getDeptId());
        return respActiveCpuCardDto;
    }

}
