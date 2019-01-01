package com.eco.wisdompark.service;

import com.eco.wisdompark.domain.dto.req.subsidy.ManualSubsidyDto;

/**
 * <p>
 * 发放补助 服务类
 * </p>
 *
 * @author litao
 * @since 2018-12-28
 */
public interface SubsidyService {

    /**
     * 手动补助
     *
     * @param manualSubsidyDto 手动补助请求对象
     */
    void manualSubsidy(ManualSubsidyDto manualSubsidyDto);

    /**
     * 自动补助
     */
    void automaticSubsidy();

}
