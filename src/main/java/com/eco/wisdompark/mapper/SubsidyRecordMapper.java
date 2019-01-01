package com.eco.wisdompark.mapper;

import com.eco.wisdompark.domain.model.SubsidyRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * CPU卡-补助记录表 Mapper 接口
 * </p>
 *
 * @author litao
 * @since 2018-12-28
 */
public interface SubsidyRecordMapper extends BaseMapper<SubsidyRecord> {

    /**
     * 批量插入补助记录
     *
     * @param subsidyRecords 补助记录
     * @return 影响行数
     */
    int insertBatch(List<SubsidyRecord> subsidyRecords);

}