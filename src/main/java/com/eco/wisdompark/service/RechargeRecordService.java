package com.eco.wisdompark.service;

import com.eco.wisdompark.domain.dto.inner.InnerCpuCardInfoDto;
import com.eco.wisdompark.domain.model.RechargeRecord;
import com.baomidou.mybatisplus.extension.service.IService;
import com.eco.wisdompark.enums.RechargeType;

import java.math.BigDecimal;

/**
 * <p>
 * CPU卡-充值记录表 服务类
 * </p>
 *
 * @author litao
 * @since 2018-12-28
 */
public interface RechargeRecordService extends IService<RechargeRecord> {

    /**
     * 保存充值记录
     * @param amount 充值金额
     * @param rechargeType 充值类型 RechargeType 枚举
     * @param importSerialNo 批量导入序列号
     * @return
     */
    boolean saveRechargeRecord(InnerCpuCardInfoDto cardInfoDto, BigDecimal amount, RechargeType rechargeType, String importSerialNo);

}
