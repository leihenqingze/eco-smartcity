package com.eco.wisdompark.mapper;

import com.eco.wisdompark.domain.model.CpuCard;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * CPU卡 Mapper 接口
 * </p>
 *
 * @author litao
 * @since 2018-12-28
 */
public interface CpuCardMapper extends BaseMapper<CpuCard> {

    /**
     * 余额充值 操作
     *
     * @param cardId
     * @param amount
     */
    void recharge(@Param("cardId") String cardId, @Param("amount") BigDecimal amount);

    /**
     * 批量更新补助金额
     *
     * @param list CPU卡集合
     * @return 影响行数
     */
    int updateBatchSubsidy(List<CpuCard> list);

    /**
     * 启停
     *
     * @param ifUsed 是否启用
     * @param cardId 卡物理机编号
     * @return 是否停用成功
     */
    int startStop(@Param("ifUsed") int ifUsed, @Param("cardId") String cardId);

}