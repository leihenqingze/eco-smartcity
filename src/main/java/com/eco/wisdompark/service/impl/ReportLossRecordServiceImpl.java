package com.eco.wisdompark.service.impl;

import com.eco.wisdompark.common.dto.ResponseData;
import com.eco.wisdompark.domain.dto.req.card.ReissueCardDto;
import com.eco.wisdompark.domain.dto.resp.RespReissueCardDto;
import com.eco.wisdompark.domain.model.ReportLossRecord;
import com.eco.wisdompark.mapper.ReportLossRecordMapper;
import com.eco.wisdompark.service.ReportLossRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 挂失记录 服务实现类
 * </p>
 *
 * @author litao
 * @since 2018-12-28
 */
@Service
public class ReportLossRecordServiceImpl extends ServiceImpl<ReportLossRecordMapper, ReportLossRecord> implements ReportLossRecordService {

    @Override
    public RespReissueCardDto reissueCard(ReissueCardDto reissueCardDto) {
        // 1.查询原来信息
        reissueCardDto.getUserId();
        // 2.将原卡信息置为挂失状态
        // 3.插入新卡信息
        // 4.插入挂失记录
        return null;
    }
}
