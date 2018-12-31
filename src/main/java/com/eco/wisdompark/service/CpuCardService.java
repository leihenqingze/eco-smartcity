package com.eco.wisdompark.service;

import com.eco.wisdompark.common.dto.ResponseData;
import com.eco.wisdompark.domain.dto.req.card.RechargeCardDto;
import com.eco.wisdompark.domain.dto.req.card.MakingCpuCardDto;
import com.eco.wisdompark.domain.dto.req.card.QueryCardInfoDto;
import com.eco.wisdompark.domain.dto.resp.RespMakingCpuCardDto;
import com.eco.wisdompark.domain.dto.resp.RespQueryCardInfoDto;
import com.eco.wisdompark.domain.dto.resp.RespQueryCardInfoListDto;
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
    RespMakingCpuCardDto makingCpuCard(MakingCpuCardDto makingCpuCardDto);

    /**
     * 查询卡片信息接口
     * @param queryCardInfoDto
     * @return
     */
    RespQueryCardInfoDto queryCardInfo(QueryCardInfoDto queryCardInfoDto);

    /**
     * 查询人员卡信息接口
     * @param queryCardInfoDto
     * @param param 标记、没有实际意义
     * @return
     */
    RespQueryCardInfoListDto queryCardInfo(QueryCardInfoDto queryCardInfoDto, String param);

    /**
     * 查询人员卡卡信息接口
     * @param userName
     * @param mobile
     * @param deptId
     * @return
     */
    List<RespQueryCardInfoDto> queryCardInfo(String userName, String mobile, Integer deptId);

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
     * 查询CPU卡片信息 是否存在？
     * @param cardId
     * @param cardSerialNo
     * @return
     */
    boolean queryCardInfoIsExist(String cardId, String cardSerialNo);

    /**
     * 查询人员对应CPU卡信息
     * @param userIds
     * @return
     * */
    List<CpuCard> getCpuCardByUserIds(List<Integer> userIds);
    /**
     * 查询人员对应CPU卡信息
     * @param userId
     * @return
     * */
    CpuCard getCpuCarByUserId(Integer userId);


    /**
     * 更新cpu卡信息
     * @param cpuCard
     */
    int updateCpuCard(CpuCard cpuCard);

}
