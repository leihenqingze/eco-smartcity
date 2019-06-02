package com.eco.wisdompark.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eco.wisdompark.common.dto.ResponseData;
import com.eco.wisdompark.common.exceptions.WisdomParkException;
import com.eco.wisdompark.common.utils.RedisUtil;
import com.eco.wisdompark.common.utils.StringTools;
import com.eco.wisdompark.domain.dto.req.user.GetUserDto;
import com.eco.wisdompark.domain.dto.req.user.SearchUserDto;
import com.eco.wisdompark.domain.dto.req.user.UserDto;
import com.eco.wisdompark.domain.dto.req.user.UserLoginDto;
import com.eco.wisdompark.domain.dto.resp.UserLoginRespDto;
import com.eco.wisdompark.domain.model.CpuCard;
import com.eco.wisdompark.domain.model.User;
import com.eco.wisdompark.mapper.UserMapper;
import com.eco.wisdompark.service.CpuCardService;
import com.eco.wisdompark.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 人员表 服务实现类
 * </p>
 *
 * @author litao
 * @since 2018-12-28
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private CpuCardService cpuCardService;

    @Autowired
    private RedisUtil redisUtil;


    @Override
    public Integer countByDept(Integer deptId) {
        QueryWrapper<User> wrapper = new QueryWrapper<User>();
        wrapper.eq("dept_id", deptId);
        return baseMapper.selectCount(wrapper);
    }

    @Override
    public IPage<UserDto> searchUserDtos(SearchUserDto searchUserDto) {
        IPage<UserDto> result = new Page<>();
        QueryWrapper<User> wrapper = new QueryWrapper<User>();
        if (searchUserDto.getDeptId() != null && searchUserDto.getDeptId() > 0) {
            wrapper.eq("dept_id", searchUserDto.getDeptId());
        }
        if (StringUtils.isNotBlank(searchUserDto.getUserName())) {
            wrapper.like("user_name", searchUserDto.getUserName());
        }
        if (StringUtils.isNotBlank(searchUserDto.getPhoneNum())) {
            wrapper.like("phone_num", searchUserDto.getPhoneNum());
        }
        IPage<User> page = baseMapper.selectPage(new Page<>(searchUserDto.getCurrentPage(), searchUserDto.getPageSize()), wrapper);
        result.setPages(page.getPages());
        result.setCurrent(page.getCurrent());
        result.setSize(page.getSize());
        result.setTotal(page.getTotal());
        List<User> list = page.getRecords();
        if (!list.isEmpty()) {
            List<UserDto> dtoList = new ArrayList<>();
            List<Integer> userIds = new ArrayList<>();
            list.forEach(e -> {
                userIds.add(e.getId());
                UserDto dto = new UserDto();
                BeanUtils.copyProperties(e, dto);
                dtoList.add(dto);
            });
            List<CpuCard> cpuCards = cpuCardService.getCpuCardByUserIds(userIds);
            if (!cpuCards.isEmpty()) {
                cpuCards.forEach(c -> {
                    dtoList.forEach(dto -> {
                        if (c.getUserId().equals(dto.getId())) {
                            dto.setCardSerialNo(c.getCardSerialNo());
                            dto.setDeposit(c.getDeposit());
                            dto.setCardSource(c.getCardSource());
                            dto.setRechargeBalance(c.getRechargeBalance());
                            dto.setSubsidyBalance(c.getSubsidyBalance());
                        }
                    });
                });
            }
            result.setRecords(dtoList);
        }
        return result;
    }


    @Override
    public User queryByUserId(Integer userId) {
        if (userId > 0) {
            return baseMapper.selectById(userId);
        }
        return null;
    }

    @Override
    public List<User> getUserListByDeptId(Integer deptId) {
        if (deptId == null) {
            return Lists.newArrayList();
        }
        QueryWrapper<User> wrapper = new QueryWrapper<User>();
        wrapper.eq("dept_id", deptId);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public UserDto getUser(GetUserDto getUserDto) {
        UserDto dto = new UserDto();
        User user = baseMapper.selectById(getUserDto.getId());
        if (user != null) {
            BeanUtils.copyProperties(user, dto);
            CpuCard cpuCard = cpuCardService.getCpuCarByUserId(user.getId());
            if (cpuCard != null) {
                dto.setCardSerialNo(cpuCard.getCardSerialNo());
                dto.setDeposit(cpuCard.getDeposit());
                dto.setCardSource(cpuCard.getCardSource());
                dto.setRechargeBalance(cpuCard.getRechargeBalance());
                dto.setSubsidyBalance(cpuCard.getSubsidyBalance());
            }
        }
        return dto;
    }

    @Override
    public List<User> getUsers(List<Integer> userIds) {
        QueryWrapper<User> wrapper = new QueryWrapper<User>();
        wrapper.in("id", userIds);
        return baseMapper.selectList(wrapper);
    }

    /**
     * 用户登录
     *
     * @param dto 登录参数
     * @return 登录信息
     */
    @Override
    public UserLoginRespDto login(UserLoginDto dto) {
        if (!isCaptcha(dto.getPhoneNum(),dto.getCaptcha())) {
            throw new WisdomParkException(ResponseData.STATUS_CODE_610, "验证码不正确");
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone_num", dto.getPhoneNum());
        User user = baseMapper.selectOne(queryWrapper);
        if (Objects.isNull(user)) {
            throw new WisdomParkException(ResponseData.STATUS_CODE_611, "用户不存在");
        }
        UserLoginRespDto respDto = new UserLoginRespDto();
        CpuCard cpuCard = cpuCardService.getCpuCarByUserId(user.getId());
        if (Objects.nonNull(cpuCard)) {
            respDto.setCardIdHex(cpuCard.getCardId());
            respDto.setCardId(StringTools.cardHexStringToDecimal(cpuCard.getCardId()));
        } else {
            respDto.setCardId("-1");
        }
        respDto.setUserName(user.getUserName());
        return respDto;
    }

    @Override
    public List<User> getAllUser() {

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public String getAb(String username) {


        return  baseMapper.getABalance(username);
    }

    /**
     * 比较验证码
     *
     * @param captcha 用户输入的验证码
     * @return 是否相等
     */
    private boolean isCaptcha(String phone,String captcha) {
        return redisUtil.get(phone).equals(captcha);
    }

}