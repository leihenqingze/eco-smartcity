package com.eco.wisdompark.service;

import com.eco.wisdompark.common.dto.ResponseData;
import com.eco.wisdompark.domain.dto.req.card.CardRechargeDto;
import com.eco.wisdompark.domain.dto.req.card.MakingCpuCardDto;
import com.eco.wisdompark.domain.dto.req.card.QueryCardInfoDto;
import com.eco.wisdompark.domain.model.CpuCard;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

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
     * @param cardRechargeDto
     * @return
     */
    ResponseData cardRecharge(CardRechargeDto cardRechargeDto);

    /**
     * 卡片批量充值接口
     * @param fileName
     * @param file
     * @return
     */
    ResponseData batchRecharge(String fileName, File file);

}
