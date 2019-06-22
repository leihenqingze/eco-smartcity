package com.eco.wisdompark.service;

import com.eco.wisdompark.domain.dto.req.PageReqDto;
import com.eco.wisdompark.domain.dto.req.subsidy.SearchAutoSubsidyRecordReq;
import com.eco.wisdompark.domain.dto.req.subsidyRecord.SearchSubsidyRecordDto;
import com.eco.wisdompark.domain.dto.resp.ManualSubsidyRecordListRespDto;
import com.eco.wisdompark.domain.dto.resp.SubsidyDetailsDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.eco.wisdompark.domain.dto.req.consumeRecord.SearchConsumeRecordDto;
import com.eco.wisdompark.domain.dto.req.subsidyRecord.SubsidyRecordDto;
import com.eco.wisdompark.domain.model.SubsidyRecord;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletResponse;

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
    IPage<ManualSubsidyRecordListRespDto> searchManualSubsidyRecord(PageReqDto<Integer> pageReqDto);

    /**
     * 查询补助记录
     *
     * @param searchSubsidyRecordDto 查询条件
     * @return 补助记录
     */
    IPage<SubsidyRecordDto> searchSubsidyRecordDtos(SearchSubsidyRecordDto searchSubsidyRecordDto);

    /**
     * 补助记录导出
     *
     * @param searchSubsidyRecordDto 查询条件
     */
    void exportSearchSubsidyRecord(SearchSubsidyRecordDto searchSubsidyRecordDto, HttpServletResponse response);

    /**
     * 统计总金额
     *
     * @param searchSubsidyRecordDto 查询条件
     * @return 总金额
     */
    Double countSubsidyRecord(SearchSubsidyRecordDto searchSubsidyRecordDto);

}