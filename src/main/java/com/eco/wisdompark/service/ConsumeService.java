package com.eco.wisdompark.service;

import com.eco.wisdompark.domain.dto.req.consume.ConsumeDto;
import com.eco.wisdompark.domain.dto.resp.ConsumeRespDto;

/**
 * 消费服务接口
 */
public interface ConsumeService {

    /**
     * 消费服务
     *
     * @param consumeDto 消费请求体
     * @return 消费详情
     */
    ConsumeRespDto consume(ConsumeDto consumeDto);

}