package com.eco.wisdompark.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.eco.wisdompark.domain.dto.req.consumeRecord.ConsumeRecordDto;
import com.eco.wisdompark.domain.dto.req.consumeRecord.FinanceConsumeRecordDto;
import com.eco.wisdompark.domain.dto.req.consumeRecord.SearchConsumeRecordDto;
import com.eco.wisdompark.domain.dto.resp.ConsomeRecordRespDto;
import com.eco.wisdompark.domain.model.ConsumeRecord;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * CPU卡-消费记录表 服务类
 * </p>
 *
 * @author litao
 * @since 2018-12-28
 */
public interface ConsumeRecordService extends IService<ConsumeRecord> {

    IPage<ConsumeRecordDto> searchUserConsumeRecordDtos(SearchConsumeRecordDto searchConsumeRecordDto);

    IPage<ConsumeRecordDto> searchFinanceConsumeRecordDtos(FinanceConsumeRecordDto financeConsumeRecordDto);

    BigDecimal totalConsomeRecordAmount(FinanceConsumeRecordDto financeConsumeRecordDto);

    List<ConsumeRecordDto> searchUserConsumeRecordDtosByCardId(String cardId);

    ConsomeRecordRespDto searchShopPosConsumeRecordDtos(SearchConsumeRecordDto searchConsumeRecordDto);

    void exportShopPosConsumeRecordDtos(SearchConsumeRecordDto searchConsumeRecordDto,HttpServletResponse response);

}
