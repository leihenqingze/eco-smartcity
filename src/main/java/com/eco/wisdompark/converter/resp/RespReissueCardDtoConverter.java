package com.eco.wisdompark.converter.resp;

import com.eco.wisdompark.domain.dto.resp.RespReissueCardDto;
import com.eco.wisdompark.domain.model.User;

import java.math.BigDecimal;

/**
 * 补卡返回类 构造
 */
public class RespReissueCardDtoConverter {

    /**
     * 补卡类 构造 Dto
     * @param oldCardId
     * @param newCardId
     * @param totalBalance
     * @param user
     * @return
     */
    public static RespReissueCardDto create(String oldCardId, String newCardId, BigDecimal totalBalance, User user){
        RespReissueCardDto respReissueCardDto = new RespReissueCardDto();
        respReissueCardDto.setOldCardId(oldCardId);
        respReissueCardDto.setNewCardId(newCardId);
        respReissueCardDto.setBalance(totalBalance);
        respReissueCardDto.setDeptId(user.getDeptId());
        respReissueCardDto.setUserName(user.getUserName());
        respReissueCardDto.setPhoneNum(user.getPhoneNum());
        respReissueCardDto.setUserCardNum(user.getUserCardNum());
        return respReissueCardDto;
    }

}
