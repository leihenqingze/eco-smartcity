package com.eco.wisdompark.service;

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
     * @param cardId cpu卡id
     * @param cardSerialNo cpu卡序列号
     * @param amount 充值金额
     * @param rechargeType 充值类型 RechargeType 枚举
     * @param importSerialNo 批量导入序列号
     * @param userId 用户id
     * @return
     */
    boolean saveRechargeRecord(String cardId, String cardSerialNo, BigDecimal amount, RechargeType rechargeType,
                           String importSerialNo, Integer userId);

}
