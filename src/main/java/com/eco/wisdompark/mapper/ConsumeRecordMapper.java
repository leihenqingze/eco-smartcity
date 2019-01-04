package com.eco.wisdompark.mapper;

import com.eco.wisdompark.domain.dto.req.consumeRecord.FinanceConsumeRecordDto;
import com.eco.wisdompark.domain.model.ConsumeRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

/**
 * <p>
 * CPU卡-消费记录表 Mapper 接口
 * </p>
 *
 * @author litao
 * @since 2018-12-28
 */
public interface ConsumeRecordMapper extends BaseMapper<ConsumeRecord> {

    BigDecimal totalConsomeRecordRechargeAmount(FinanceConsumeRecordDto financeConsumeRecordDto);

    BigDecimal totalConsomeRecordSubsidyAmount(FinanceConsumeRecordDto financeConsumeRecordDto);

}
