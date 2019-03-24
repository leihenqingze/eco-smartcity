package com.eco.wisdompark.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.eco.wisdompark.domain.dto.req.bus.BusRecordDto;
import com.eco.wisdompark.domain.dto.req.bus.SearchBusDto;
import com.eco.wisdompark.domain.dto.req.bus.SearchBusRecordDto;
import com.eco.wisdompark.domain.model.Bus;
import com.eco.wisdompark.domain.model.BusRecord;
import com.baomidou.mybatisplus.extension.service.IService;
import com.eco.wisdompark.domain.model.SysUser;

import java.util.List;

/**
 * <p>
 * 班车乘车记录 服务类
 * </p>
 *
 * @author zhangkai
 * @since 2019-03-24
 */
public interface BusRecordService extends IService<BusRecord> {

    /**
     * 根据条件查询班车信息
     * @param searchBusRecordDto
     * @return
     */
    IPage<BusRecordDto>  getBusRecordByQuery(SearchBusRecordDto searchBusRecordDto);

}
