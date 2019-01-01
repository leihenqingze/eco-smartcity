package com.eco.wisdompark.service;

import com.eco.wisdompark.domain.dto.req.PageReqDto;
import com.eco.wisdompark.domain.dto.req.subsidy.SearchAutoSubsidyRecordReq;
import com.eco.wisdompark.domain.dto.resp.SubsidyDetailsDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.eco.wisdompark.domain.dto.req.consumeRecord.SearchConsumeRecordDto;
import com.eco.wisdompark.domain.dto.req.subsidyRecord.SubsidyRecordDto;
import com.eco.wisdompark.domain.dto.resp.SubsidyRecordListRespDto;
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
    SubsidyDetailsDto searchAutoSubsidyRecord(SearchAutoSubsidyRecordReq searchAutoSubsidyRecordReq);

    IPage<SubsidyRecordDto> searchUserSubsidyRecordDtos(SearchConsumeRecordDto searchConsumeRecordDto);

    /**
     * 根据部门ID查询手动补助
     *
     * @param pageReqDto 分页对象
     * @return 补助记录
     */
    IPage<SubsidyRecordListRespDto> searchManualSubsidyRecord(PageReqDto<Integer> pageReqDto);

}