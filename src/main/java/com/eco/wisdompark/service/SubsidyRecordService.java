package com.eco.wisdompark.service;

import com.eco.wisdompark.domain.dto.req.subsidy.SearchAutoSubsidyRecordReq;
import com.eco.wisdompark.domain.dto.resp.SubsidyDetailsDto;
import com.eco.wisdompark.domain.model.SubsidyRecord;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * CPU卡-补助记录表 服务类
 * </p>
 *
 * @author litao
 * @since 2018-12-28
 */
public interface SubsidyRecordService extends IService<SubsidyRecord> {

    /**
     * 根据补助规则和时间获取自动补助的记录
     *
     * @param searchAutoSubsidyRecordReq 查询对象
     * @return 补助详情
     */
    SubsidyDetailsDto searchDeptSubsidyRecord(SearchAutoSubsidyRecordReq searchAutoSubsidyRecordReq);

}