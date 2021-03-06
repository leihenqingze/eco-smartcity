package com.eco.wisdompark.service;

import com.eco.wisdompark.domain.dto.inner.InnerCpuCardInfoDto;
import com.eco.wisdompark.domain.model.ChangeAmount;
import com.baomidou.mybatisplus.extension.service.IService;
import com.eco.wisdompark.enums.AmountChangeType;

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
     * 保存 充值 金额变动记录表
     * @param cardInfoDto
     * @param changeAmt
     * @param changeType
     * @return
     */
    boolean saveRechargeChanageAmountRecord(InnerCpuCardInfoDto cardInfoDto, BigDecimal changeAmt, AmountChangeType changeType);

}
