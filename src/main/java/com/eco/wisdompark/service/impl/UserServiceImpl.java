package com.eco.wisdompark.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eco.wisdompark.common.dto.ResponseData;
import com.eco.wisdompark.common.exceptions.WisdomParkException;
import com.eco.wisdompark.common.utils.RedisUtil;
import com.eco.wisdompark.common.utils.StringTools;
import com.eco.wisdompark.domain.dto.req.dept.AddLevel2DeptDto;
import com.eco.wisdompark.domain.dto.req.dept.DeptDto;
import com.eco.wisdompark.domain.dto.req.user.*;
import com.eco.wisdompark.domain.dto.resp.UserLoginRespDto;
import com.eco.wisdompark.domain.dto.resp.UserSearchRespDto;
import com.eco.wisdompark.domain.model.CpuCard;
import com.eco.wisdompark.domain.model.Dept;
import com.eco.wisdompark.domain.model.User;
import com.eco.wisdompark.mapper.UserMapper;
import com.eco.wisdompark.service.CpuCardService;
import com.eco.wisdompark.service.DeptService;
import com.eco.wisdompark.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
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

    @Autowired
    private DeptService deptService;


    @Override
    public Integer countByDept(Integer deptId) {
        QueryWrapper<User> wrapper = new QueryWrapper<User>();
        wrapper.eq("dept_id", deptId);
        return baseMapper.selectCount(wrapper);
    }

    @Override
    public UserSearchRespDto searchUserDtos(SearchUserDto searchUserDto) {

        UserSearchRespDto userSearchRespDto = new UserSearchRespDto();
        IPage<UserDto> result = new Page<>();
        BigDecimal currentPageRechargeAmount = BigDecimal.ZERO;
        BigDecimal currentPageSubsidyAmount = BigDecimal.ZERO;
        BigDecimal totalRechargeAmount = BigDecimal.ZERO;
        BigDecimal totalSubsidyAmount = BigDecimal.ZERO;
        List<Integer> deptIdList = Lists.newArrayList();

        if (searchUserDto.getDeptId() != null) {
            deptIdList.add(searchUserDto.getDeptId());
            // 查询二级部门
            Dept dept = deptService.getById(searchUserDto.getDeptId());
            if (dept != null && (dept.getDeptUpId() == null || dept.getDeptUpId() == 0)) {
                List<DeptDto> level2DeptList = getLevel2Dept(dept.getId());
                if (!CollectionUtils.isEmpty(level2DeptList)) {
                    deptIdList.clear();
                    level2DeptList.forEach(e -> {
                        deptIdList.add(e.getId());
                    });
                }
            }
        }

        QueryWrapper<User> wrapper = new QueryWrapper<User>();
        if (StringUtils.isNotBlank(searchUserDto.getCardSerialNo())) {
            Integer userId = cpuCardService.getUserId(searchUserDto.getCardSerialNo());
            if (userId != null && userId > 0) {
                wrapper.eq("id", userId);
            }
        }
        if (StringUtils.isNotBlank(searchUserDto.getUserName())) {
            wrapper.like("user_name", searchUserDto.getUserName());
        }
        if (StringUtils.isNotBlank(searchUserDto.getPhoneNum())) {
            wrapper.like("phone_num", searchUserDto.getPhoneNum());
        }
        if (!CollectionUtils.isEmpty(deptIdList)) {
            wrapper.in("dept_id", deptIdList);
        }
        // 统计所有金额
        List<User> userList = baseMapper.selectList(wrapper);
        if (!userList.isEmpty()) {
            List<Integer> userIds = new ArrayList<>();
            userList.forEach(e -> {
                userIds.add(e.getId());
            });
            List<CpuCard> cpuCards = cpuCardService.getCpuCardByUserIds(userIds);
            if (!cpuCards.isEmpty()) {
                for (CpuCard cpuCard : cpuCards) {
                    totalRechargeAmount = totalRechargeAmount.add(cpuCard.getRechargeBalance());
                    totalSubsidyAmount = totalSubsidyAmount.add(cpuCard.getSubsidyBalance());
                }
            }
        }
        // 用户分页信息
        IPage<User> page = baseMapper.selectPage(new Page<>(searchUserDto.getCurrentPage(), searchUserDto.getPageSize()), wrapper);
        result.setPages(page.getPages());
        result.setCurrent(page.getCurrent());
        result.setSize(page.getSize());
        result.setTotal(page.getTotal());
        List<User> list = page.getRecords();
        if (!list.isEmpty()) {
            List<Dept> deptlist = deptService.findtDeptAll();
            List<UserDto> dtoList = new ArrayList<>();
            List<Integer> userIds = new ArrayList<>();
            list.forEach(e -> {
                userIds.add(e.getId());
                UserDto dto = new UserDto();
                BeanUtils.copyProperties(e, dto);
                dtoList.add(dto);
            });
            if (deptlist != null && deptlist.size() > 0) {
                dtoList.forEach(dto -> {
                    deptlist.forEach(dept -> {
                        if (dto.getDeptId().equals(dept.getId())) {
                            dto.setDeptName(dept.getDeptName());
                        }
                    });
                });
            }
            List<CpuCard> cpuCards = cpuCardService.getCpuCardByUserIds(userIds);
            if (!cpuCards.isEmpty()) {
                for (CpuCard c : cpuCards) {
                    for (UserDto dto : dtoList) {
                        if (c.getUserId().equals(dto.getId())) {
                            dto.setCardSerialNo(c.getCardSerialNo());
                            dto.setDeposit(c.getDeposit());
                            dto.setCardSource(c.getCardSource());
                            dto.setRechargeBalance(c.getRechargeBalance());
                            dto.setSubsidyBalance(c.getSubsidyBalance());
                            dto.setCardId(c.getCardId());
                            dto.setCardIfUsed(c.getIfUsed());
                            currentPageRechargeAmount = currentPageRechargeAmount.add(c.getRechargeBalance());
                            currentPageSubsidyAmount = currentPageSubsidyAmount.add(c.getSubsidyBalance());
                        }
                    }
                }
            }
            result.setRecords(dtoList);
        }
        userSearchRespDto.setUserDtoIPage(result);
        userSearchRespDto.setCurrentPageRechargeAmount(currentPageRechargeAmount);
        userSearchRespDto.setCurrentPageSubsidyAmount(currentPageSubsidyAmount);
        userSearchRespDto.setTotalRechargeAmount(totalRechargeAmount);
        userSearchRespDto.setTotalSubsidyAmount(totalSubsidyAmount);
        return userSearchRespDto;
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
        if (!isCaptcha(dto.getPhoneNum(), dto.getCaptcha())) {
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


        return baseMapper.getABalance(username);
    }

    @Override
    public List<User> getListByQuery(SearchUserDto searchUserDto) {
        QueryWrapper<User> wrapper = new QueryWrapper<User>();

        List<Integer> deptIdList = Lists.newArrayList();

        if (searchUserDto.getDeptId() != null) {
            deptIdList.add(searchUserDto.getDeptId());
            // 查询二级部门
            Dept dept = deptService.getById(searchUserDto.getDeptId());
            if (dept != null && (dept.getDeptUpId() == null || dept.getDeptUpId() == 0)) {
                List<DeptDto> level2DeptList = getLevel2Dept(dept.getId());
                if (!CollectionUtils.isEmpty(level2DeptList)) {
                    deptIdList.clear();
                    level2DeptList.forEach(e -> {
                        deptIdList.add(e.getId());
                    });
                }
            }
        }

        if (StringUtils.isNotBlank(searchUserDto.getUserName())) {
            wrapper.like("user_name", searchUserDto.getUserName());
        }
        if (StringUtils.isNotBlank(searchUserDto.getPhoneNum())) {
            wrapper.like("phone_num", searchUserDto.getPhoneNum());
        }
        if (!CollectionUtils.isEmpty(deptIdList)) {
            wrapper.in("dept_id", deptIdList);
        }

        return baseMapper.selectList(wrapper);
    }

    @Override
    public Integer updateUserInfo(UpdateUserInfoDto updateUserInfoDto) {
        User user = baseMapper.selectById(updateUserInfoDto.getId());
        if (user == null) {
            throw new WisdomParkException(ResponseData.STATUS_CODE_600, "用户不存在");
        }
        if(StringUtils.isBlank(updateUserInfoDto.getPhoneNum()) ){
            throw new WisdomParkException(ResponseData.STATUS_CODE_400, "手机号不能为空");
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone_num", updateUserInfoDto.getPhoneNum())
                .or()
                .eq("user_card_num", updateUserInfoDto.getUserCardNum());
        List<User> userList = baseMapper.selectList(queryWrapper);
        if(userList!=null && userList.size()>1 ){
            throw new WisdomParkException(ResponseData.STATUS_CODE_400, "手机号或者身份证号已经存在");
        }
        user.setPhoneNum(updateUserInfoDto.getPhoneNum());
        user.setUserName(updateUserInfoDto.getUserName());
        user.setUserCardNum(updateUserInfoDto.getUserCardNum());
        Integer result = baseMapper.updateById(user);
        return result;
    }




    /**
     * 比较验证码
     *
     * @param captcha 用户输入的验证码
     * @return 是否相等
     */
    private boolean isCaptcha(String phone, String captcha) {
        return redisUtil.get(phone).equals(captcha);
    }

    private List<DeptDto> getLevel2Dept(Integer deptId) {
        AddLevel2DeptDto addLevel2DeptDto = new AddLevel2DeptDto();
        addLevel2DeptDto.setId(deptId);
        return deptService.getLevel2Dept(addLevel2DeptDto);
    }

}