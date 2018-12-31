package com.eco.wisdompark.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.eco.wisdompark.domain.dto.req.PageReqDto;
import com.eco.wisdompark.domain.dto.req.subsidy.ListSubsidyRuleReqDto;
import com.eco.wisdompark.domain.dto.resp.ListSubsidyRuleRespDto;
import com.eco.wisdompark.domain.model.SubsidyRule;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 自动补助-补助规则 服务类
 * </p>
 *
 * @author litao
 * @since 2018-12-28
 */
public interface SubsidyRuleService extends IService<SubsidyRule> {

    /**
     * 自动补助规则分页查询
     *
     * @param pageReqDto 分页信息
     * @return 自动补助规则列表
     */
    IPage<ListSubsidyRuleRespDto> findByDeptIdPage(PageReqDto<ListSubsidyRuleReqDto> pageReqDto);

}
