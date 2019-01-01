package com.eco.wisdompark.mapper;

import com.eco.wisdompark.domain.model.ChangeAmount;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 金额变动记录 Mapper 接口
 * </p>
 *
 * @author litao
 * @since 2018-12-28
 */
public interface ChangeAmountMapper extends BaseMapper<ChangeAmount> {

    /**
     * 批量插入金额变动记录
     *
     * @param changeAmounts 金额变动记录
     * @return 影响行数
     */
    int insertBatch(List<ChangeAmount> changeAmounts);

}
