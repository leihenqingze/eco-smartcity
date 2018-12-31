package com.eco.wisdompark.service;

import com.eco.wisdompark.common.dto.ResponseData;
import com.eco.wisdompark.domain.dto.req.card.RechargeCardDto;
import com.eco.wisdompark.domain.dto.req.card.MakingCpuCardDto;
import com.eco.wisdompark.domain.dto.req.card.QueryCardInfoDto;
import com.eco.wisdompark.domain.dto.resp.RespQueryCardInfoDto;
import com.eco.wisdompark.domain.model.CpuCard;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.File;
import java.util.List;

/**
 * <p>
 * CPU卡 服务类
 * </p>
 *
 * @author litao
 * @since 2018-12-28
 */
public interface CpuCardService extends IService<CpuCard> {

    /**
     * 制卡/卡片激活接口
     * @param makingCpuCardDto
     * @return
     */
    ResponseData makingCpuCard(MakingCpuCardDto makingCpuCardDto);

    /**
     * 查询卡片信息接口
     * @param queryCardInfoDto
     * @return
     */
    ResponseData queryCardInfo(QueryCardInfoDto queryCardInfoDto);

    /**
     * 卡片余额充值接口
     * @param rechargeCardDto
     * @return
     */
    boolean rechargeSingle(RechargeCardDto rechargeCardDto);

    /**
     * 卡片批量充值接口
     * @param fileName
     * @param file
     * @return
     */
    ResponseData rechargeBatch(String fileName, File file);

    /**
     * 查询CPU卡片信息 by cardId
     * @param cardId
     * @return
     */
    RespQueryCardInfoDto queryCardInfoByCardId(String cardId);

    /**
     * 查询人员对应CPU卡信息
     * @param userIds
     * @return
     * */
    List<CpuCard> getCpuCardByUserIds(List<Integer> userIds);


}
