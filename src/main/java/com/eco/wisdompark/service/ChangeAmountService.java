package com.eco.wisdompark.service;

import com.eco.wisdompark.domain.dto.CpuCardInfoDto;
import com.eco.wisdompark.domain.model.ChangeAmount;
import com.baomidou.mybatisplus.extension.service.IService;
import com.eco.wisdompark.enums.ChangeType;

import java.math.BigDecimal;

/**
 * <p>
 * 金额变动记录 服务类
 * </p>
 *
 * @author litao
 * @since 2018-12-28
 */
public interface ChangeAmountService extends IService<ChangeAmount> {

    /**
     * 保存金额变动记录表
     * @param cardInfoDto
     * @param changeAmt
     * @param changeType
     * @return
     */
    boolean saveChanageAmountRecord(CpuCardInfoDto cardInfoDto, BigDecimal changeAmt, ChangeType changeType);

}
