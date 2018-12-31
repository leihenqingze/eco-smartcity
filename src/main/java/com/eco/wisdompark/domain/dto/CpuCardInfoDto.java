package com.eco.wisdompark.domain.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class CpuCardInfoDto extends CpuCardBaseDto {

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * cpu卡id
     */
    private String cardId;

    /**
     * 卡序列号
     */
    private String cardSerialNo;

    /**
     * CUP卡充值余额
     */
    private BigDecimal rechargeBalance;

    /**
     * CUP卡补助余额
     */
    private BigDecimal subsidyBalance;

}
