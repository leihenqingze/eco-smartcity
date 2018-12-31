package com.eco.wisdompark.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.eco.wisdompark.common.dto.ResponseData;
import com.eco.wisdompark.common.exceptions.WisdomParkException;
import com.eco.wisdompark.domain.dto.CpuCardInfoDto;
import com.eco.wisdompark.domain.dto.req.card.RechargeCardDto;
import com.eco.wisdompark.domain.dto.req.card.MakingCpuCardDto;
import com.eco.wisdompark.domain.dto.req.card.QueryCardInfoDto;
import com.eco.wisdompark.domain.dto.resp.RespMakingCpuCardDto;
import com.eco.wisdompark.domain.dto.resp.RespQueryCardInfoDto;
import com.eco.wisdompark.domain.model.CpuCard;
import com.eco.wisdompark.domain.model.User;
import com.eco.wisdompark.enums.*;
import com.eco.wisdompark.mapper.CpuCardMapper;
import com.eco.wisdompark.service.ChangeAmountService;
import com.eco.wisdompark.service.CpuCardService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eco.wisdompark.service.RechargeRecordService;
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

    @Autowired
    private RechargeRecordService rechargeRecordService;

    @Autowired
    private ChangeAmountService changeAmountService;

    @Autowired
    private CpuCardMapper cpuCardMapper;


    @Override
    @Transactional
    public RespMakingCpuCardDto makingCpuCard(MakingCpuCardDto makingCpuCardDto) {
        RespMakingCpuCardDto respMakingCpuCardDto = new RespMakingCpuCardDto();
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
        return respMakingCpuCardDto;
    }

    @Override
    public RespQueryCardInfoDto queryCardInfo(QueryCardInfoDto queryCardInfoDto) {
        RespQueryCardInfoDto respQueryCardInfoDto = null;
        // 1.根据CPU卡id查询
        String cardId = queryCardInfoDto.getCardId();
        if (!StringUtils.isEmpty(cardId)){
            respQueryCardInfoDto = queryCardInfoByCardId(cardId);
            if (respQueryCardInfoDto != null){
                return respQueryCardInfoDto;
            }
        }

        // 2.根据用户信息查询
        String userName = queryCardInfoDto.getUserName();
        String mobile = queryCardInfoDto.getPhoneNum();
        int deptId = queryCardInfoDto.getDeptId();
        respQueryCardInfoDto = queryCardInfoByUserInfo(userName, mobile, deptId);
        if (respQueryCardInfoDto != null){
            return respQueryCardInfoDto;
        }

        log.error("queryCardInfo user or cpuCard not exists... cardId:{}, userName:{}, phoneNum:{}, deptId:{}",
                queryCardInfoDto.getCardId(), queryCardInfoDto.getUserName(), queryCardInfoDto.getPhoneNum(), queryCardInfoDto.getDeptId());
        return null;
    }

    @Override
    @Transactional
    public boolean rechargeSingle(RechargeCardDto rechargeCardDto) {
        // 1.校验CPU卡是否存在
        CpuCardInfoDto cpuCardInfoDto = queryCardInfoByCardId(rechargeCardDto.getCardId(), null);
        if (cpuCardInfoDto == null){
            throw new WisdomParkException(ResponseData.STATUS_CODE_601, "用户或卡信息不存在");
        }

        //  2.进行充值操作（变更卡余额 、保存充值记录、增加金额变更记录）
        return rechargeBalance(cpuCardInfoDto, rechargeCardDto.getCardId(),rechargeCardDto.getRechargeAmt());
    }

    @Override
    public ResponseData rechargeBatch(String fileName, File file) {
        return null;
    }


    /**
     * 查询 卡信息 内部使用
     * @param cardId
     * @param param 没有实际意义，与另外一个方法区分
     * @return
     */
    public CpuCardInfoDto queryCardInfoByCardId(String cardId, String param){
        if (StringUtils.isEmpty(cardId)){
            return null;
        }

        QueryWrapper<CpuCard> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("card_id", cardId);
        CpuCard cpuCard = getOne(queryWrapper);
        if (cpuCard != null){
            log.info("queryCardInfoByCardId  inner cardId:{}, no data...", cardId);
            return null;
        }

        CpuCardInfoDto cardInfoDto = new CpuCardInfoDto();
        cardInfoDto.setCardId(cardId);
        cardInfoDto.setCardSerialNo(cpuCard.getCardSerialno());
        cardInfoDto.setUserId(cpuCard.getUserId());
        return cardInfoDto;
    }

    @Override
    public RespQueryCardInfoDto queryCardInfoByCardId(String cardId){
        if (StringUtils.isEmpty(cardId)){
            return null;
        }

        // TODO 可以加一层缓存
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

    @Override
    public List<CpuCard> getCpuCardByUserIds(List<Integer> userIds) {
        if(userIds!=null && userIds.size()>0 ){
            QueryWrapper<CpuCard> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("report_loss_ststus",ReportLossStstus.IN_USE.getCode());
            queryWrapper.in("user_id",userIds);
            List<CpuCard> list=baseMapper.selectList(queryWrapper);
            return list;
        }
        return null;
    }


    /**
     * 单个人员 充值操作
     * (非共享数据且无并发)，所以无需加锁
     * @param cardInfoDto
     * @param cardId
     * @param amount
     * @return
     */
    private boolean rechargeBalance(CpuCardInfoDto cardInfoDto, String cardId, BigDecimal amount){
        //  1.充值操作
        int result = cpuCardMapper.recharge(cardId, amount);
        if (result <= 0){
            return false;
        }
        // 2.保存充值记录
        rechargeRecordService.saveRechargeRecord(cardInfoDto, amount, RechargeType.MANUAL, null);
        // 3.保存金额变更记录
        changeAmountService.saveChanageAmountRecord(cardInfoDto, amount, AmountChangeType.TOP_UP);
        return true;
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
