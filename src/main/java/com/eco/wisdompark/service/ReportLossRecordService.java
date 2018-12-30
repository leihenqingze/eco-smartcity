package com.eco.wisdompark.service;

import com.eco.wisdompark.common.dto.ResponseData;
import com.eco.wisdompark.domain.dto.req.card.ReissueCardDto;
import com.eco.wisdompark.domain.model.ReportLossRecord;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 挂失记录 服务类
 * </p>
 *
 * @author litao
 * @since 2018-12-28
 */
public interface ReportLossRecordService extends IService<ReportLossRecord> {

    /**
     * 卡片挂失补发接口
     * @param reissueCardDto
     * @return
     */
    ResponseData reissueCard(ReissueCardDto reissueCardDto);

}
