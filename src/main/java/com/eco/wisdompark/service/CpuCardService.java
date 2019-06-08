package com.eco.wisdompark.service;

import com.eco.wisdompark.domain.dto.req.card.*;
import com.eco.wisdompark.domain.dto.resp.*;
import com.eco.wisdompark.domain.model.CpuCard;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;
import java.math.BigDecimal;
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
     * 制卡接口
     * @param makingCpuCardDto
     * @return
     */
    RespMakingCpuCardDto makingCpuCard(MakingCpuCardDto makingCpuCardDto);

    /**
     * 卡片激活接口
     * @param activeCpuCardDto
     * @return
     */
    RespActiveCpuCardDto activeCpuCard(ActiveCpuCardDto activeCpuCardDto);

    /**
     * 查询卡片信息接口【充值使用】
     * @param queryCardInfoDto
     * @return
     */
    RespQueryCardInfoDto queryCardInfo(QueryCardInfoDto queryCardInfoDto);

    /**
     * 查询人员卡信息列表接口【挂失使用】
     * @param lossQueryCardInfoDto
   * @return
     */
    RespQueryCardInfoListDto queryCardInfoList(LossQueryCardInfoDto lossQueryCardInfoDto);

    /**
     * 挂失查询人员卡信息确认接口【挂失使用】
     * @param lossQueryConfirmDto
     * @return
     */
    RespLossQueryConfirmDto queryCardInfo(LossQueryConfirmDto lossQueryConfirmDto);

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
     * @param fileCode
     * @return
     */
    boolean rechargeBatch(String fileCode);

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
     * 通过卡编号查询人员Id
     * @param card_serialNo
     * @return
     * */
    Integer getUserId(String card_serialNo);


    /**
     * 更新cpu卡信息
     * @param cpuCard
     */
    int updateCpuCard(CpuCard cpuCard);


    RespRechargeBatchDataDto fileUpload(MultipartFile file);

    RespQueryAmountDto queryAmount(@RequestBody QueryCardInfoDto queryCardInfoDto);

    int updateCpuCardBalance(Integer userId, BigDecimal rechargeBalance);

    int updateCpuCardSBalance(Integer userId, BigDecimal subsidyBalance);

    BatchMarkingCardRespDto batchMakingCard(MultipartFile file);

}
