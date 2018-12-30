package com.eco.wisdompark.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.eco.wisdompark.common.dto.ResponseData;
import com.eco.wisdompark.common.exceptions.WisdomParkException;
import com.eco.wisdompark.domain.dto.req.card.CardRechargeDto;
import com.eco.wisdompark.domain.dto.req.card.MakingCpuCardDto;
import com.eco.wisdompark.domain.dto.req.card.QueryCardInfoDto;
import com.eco.wisdompark.domain.dto.resp.RespMakingCpuCardDto;
import com.eco.wisdompark.domain.dto.resp.RespQueryCardInfoDto;
import com.eco.wisdompark.domain.model.CpuCard;
import com.eco.wisdompark.domain.model.User;
import com.eco.wisdompark.enums.CardType;
import com.eco.wisdompark.enums.ReportLossStstus;
import com.eco.wisdompark.enums.YesNo;
import com.eco.wisdompark.mapper.CpuCardMapper;
import com.eco.wisdompark.service.CpuCardService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eco.wisdompark.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * CPU卡 服务实现类
 * </p>
 *
 * @author litao
 * @since 2018-12-28
 */
@Slf4j
@Service
public class CpuCardServiceImpl extends ServiceImpl<CpuCardMapper, CpuCard> implements CpuCardService {

    @Autowired
    private UserService userService;

    @Override
    @Transactional
    public ResponseData makingCpuCard(MakingCpuCardDto makingCpuCardDto) {
        ResponseData responseData = new ResponseData();
        RespMakingCpuCardDto respMakingCpuCardDto = new RespMakingCpuCardDto();
        try{
            // 1.校验用户是否存在
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("phone_num", makingCpuCardDto.getPhoneNum());
            List<User> userList = userService.list(queryWrapper);
            if (!CollectionUtils.isEmpty(userList)){
                log.error("makingCpuCard phoneNum:{}, user already exist...", makingCpuCardDto.getPhoneNum());
                throw new WisdomParkException(ResponseData.STATUS_CODE_600, "user already exist");
            }

            // 2.保存用户信息
            User user = saveUser(makingCpuCardDto);

            // 3.保存制卡信息
            Integer userId = user.getId();
            CpuCard cpuCard = saveCpuCardInfo(makingCpuCardDto, userId);

            // 4.封装返回信息
            respMakingCpuCardDto.setId(cpuCard.getId());
            respMakingCpuCardDto.setUserName(user.getUserName());
            respMakingCpuCardDto.setPhoneNum(user.getPhoneNum());
            respMakingCpuCardDto.setDeptId(user.getDeptId());
            respMakingCpuCardDto.setDeposit(cpuCard.getDeposit());
            responseData.setData(respMakingCpuCardDto);
            responseData.OK();
        }catch (Exception e){
            log.error("makingCpuCard Exception... phoneNum:{}", makingCpuCardDto.getPhoneNum());
            e.printStackTrace();
        }
        return responseData;
    }

    @Override
    public ResponseData queryCardInfo(QueryCardInfoDto queryCardInfoDto) {
        ResponseData responseData = new ResponseData();

        // 1.根据CPU卡id查询
        String cardId = queryCardInfoDto.getCardId();
        if (!StringUtils.isEmpty(cardId)){
            RespQueryCardInfoDto respQueryCardInfoDto = queryCardInfoByCardId(cardId);
            if (respQueryCardInfoDto != null){
                responseData.setData(respQueryCardInfoDto);
                responseData.OK();
                return responseData;
            }
        }

        // 2.根据用户信息查询
        String userName = queryCardInfoDto.getUserName();
        String mobile = queryCardInfoDto.getPhoneNum();
        int deptId = queryCardInfoDto.getDeptId();
        RespQueryCardInfoDto respQueryCardInfoDto = queryCardInfoByUserInfo(userName, mobile, deptId);
        if (respQueryCardInfoDto != null){
            responseData.setData(respQueryCardInfoDto);
            responseData.OK();
            return responseData;
        }

        responseData.ERROR(responseData.STATUS_CODE_601, "用户或卡信息不存在");
        return responseData;
    }

    @Override
    public ResponseData cardRecharge(CardRechargeDto cardRechargeDto) {
        return null;
    }

    @Override
    public ResponseData batchRecharge(String fileName, File file) {
        return null;
    }

    @Override
    public RespQueryCardInfoDto queryCardInfoByCardId(String cardId){
        if (StringUtils.isEmpty(cardId)){
            return null;
        }

        QueryWrapper<CpuCard> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("card_id", cardId);
        List<CpuCard> cpuCards = list(queryWrapper);
        if (CollectionUtils.isEmpty(cpuCards)){
            log.info("queryCardInfoByCardId cardId:{}, no data...", cardId);
            return null;
        }

        CpuCard cpuCard = cpuCards.get(0);
        Integer userId = cpuCard.getUserId();
        User user = userService.getById(userId);
        if (user != null){
            // 封装返回信息
            RespQueryCardInfoDto respQueryCardInfoDto = userCardDataConvert(user, cpuCard);
            return respQueryCardInfoDto;
        }
        log.error("queryCardInfoByCardId cardId:{}, userId:{}, exists but user not exists ERROR ...", cardId, userId);
        return null;
    }


    /**
     * 根据用户信息查询CPU卡信息
     * @param userName
     * @param mobile
     * @param deptId
     * @return
     */
    private RespQueryCardInfoDto queryCardInfoByUserInfo(String userName, String mobile, Integer deptId){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(userName)){
            queryWrapper.eq("user_name", userName);
        }
        if (!StringUtils.isEmpty(mobile)){
            queryWrapper.eq("phone_num", mobile);
        }
        if (!StringUtils.isEmpty(deptId) && deptId > 0){
            queryWrapper.eq("dept_id", deptId);
        }

        RespQueryCardInfoDto respQueryCardInfoDto = null;
        Integer userId = 0;
        User user = userService.getOne(queryWrapper);
        log.info("queryCardInfoByUserInfo no data userName:{}, mobile:{}, deptId:{}", userName, mobile, deptId);
        if (user != null && user.getId() > 0){
            userId = user.getId();
            QueryWrapper<CpuCard> cpuCardQueryWrapper = new QueryWrapper<>();
            cpuCardQueryWrapper.eq("user_id", userId);
            cpuCardQueryWrapper.eq("report_loss_ststus", ReportLossStstus.IN_USE.getCode());
            cpuCardQueryWrapper.eq("del", YesNo.NO.getCode());
            CpuCard cpuCard = getOne(cpuCardQueryWrapper);
            if (cpuCard != null){
                respQueryCardInfoDto = userCardDataConvert(user, cpuCard);
                return respQueryCardInfoDto;
            }
            log.info("queryCardInfoByUserInfo no data userId:{},reportLossStstus:{}, del:{}", userId, ReportLossStstus.IN_USE, YesNo.NO);
        }
        return null;
    }


    /**
     * 组装人员卡信息
     * @param user
     * @param cpuCard
     * @return
     */
    private RespQueryCardInfoDto userCardDataConvert(User user, CpuCard cpuCard){
        RespQueryCardInfoDto respQueryCardInfoDto = new RespQueryCardInfoDto();
        respQueryCardInfoDto.setId(cpuCard.getId());
        respQueryCardInfoDto.setUserName(user.getUserName());
        respQueryCardInfoDto.setUserCardNum(user.getUserCardNum());
        respQueryCardInfoDto.setDeptId(user.getDeptId());
        respQueryCardInfoDto.setPhoneNum(user.getPhoneNum());
        BigDecimal rechargeBalance = cpuCard.getRechargeBalance() != null ? cpuCard.getRechargeBalance() : new BigDecimal(0);
        BigDecimal subsidyBalance = cpuCard.getSubsidyBalance() != null ? cpuCard.getSubsidyBalance() : new BigDecimal(0);
        respQueryCardInfoDto.setRechargeBalance(rechargeBalance);
        respQueryCardInfoDto.setSubsidyBalance(cpuCard.getSubsidyBalance());
        respQueryCardInfoDto.setTotalBalance(rechargeBalance.add(subsidyBalance));
        return respQueryCardInfoDto;
    }


    /**
     * 保存用户信息入库
     * @param makingCpuCardDto
     * @return
     */
    public User saveUser(MakingCpuCardDto makingCpuCardDto){
        User user = new User();
        user.setUserName(makingCpuCardDto.getUserName());
        user.setUserCardNum(makingCpuCardDto.getUserCardNum());
        user.setDeptId(makingCpuCardDto.getDeptId());
        user.setPhoneNum(makingCpuCardDto.getPhoneNum());
        user.setCreateTime(LocalDateTime.now());
        user.setDel(YesNo.NO.getCode());
        boolean res = userService.save(user);
        log.info("saveUser phoneNum:{}, res:{}", makingCpuCardDto.getPhoneNum(), res);
        return user;
    }

    private CpuCard saveCpuCardInfo(MakingCpuCardDto makingCpuCardDto, Integer userId){
        CpuCard cpuCard = new CpuCard();
        cpuCard.setUserId(userId);
        cpuCard.setCardId(makingCpuCardDto.getCardId());
        cpuCard.setCardSerialno(makingCpuCardDto.getCardSerialno());
        cpuCard.setCardType(CardType.CPU.getCode());
        cpuCard.setCardSource(makingCpuCardDto.getCardSource());
        cpuCard.setCreateTime(LocalDateTime.now());
        cpuCard.setDeposit(makingCpuCardDto.getDeposit());
        cpuCard.setRechargeBalance(new BigDecimal(0));
        cpuCard.setSubsidyBalance(new BigDecimal(0));
        cpuCard.setDel(YesNo.NO.getCode());
        cpuCard.setReportLossStstus(ReportLossStstus.IN_USE.getCode());
        boolean res = save(cpuCard);
        log.info("saveCpuCardInfo phoneNum:{}, res:{}", makingCpuCardDto.getPhoneNum(), res);
        return cpuCard;
    }

}
