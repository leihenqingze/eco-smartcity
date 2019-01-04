package com.eco.wisdompark.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eco.wisdompark.common.utils.LocalDateTimeUtils;
import com.eco.wisdompark.converter.req.PageReqDtoToPageConverter;
import com.eco.wisdompark.domain.dto.req.PageReqDto;
import com.eco.wisdompark.domain.dto.req.consumeRecord.SearchConsumeRecordDto;
import com.eco.wisdompark.domain.dto.req.subsidy.SearchAutoSubsidyRecordReq;
import com.eco.wisdompark.domain.dto.req.subsidyRecord.SubsidyRecordDto;
import com.eco.wisdompark.domain.dto.resp.ManualSubsidyRecordListRespDto;
import com.eco.wisdompark.domain.dto.resp.SubsidyDetailsDto;
import com.eco.wisdompark.domain.dto.resp.SubsidyRecordListRespDto;
import com.eco.wisdompark.domain.model.Dept;
import com.eco.wisdompark.domain.model.SubsidyRecord;
import com.eco.wisdompark.domain.model.SubsidyRule;
import com.eco.wisdompark.domain.model.User;
import com.eco.wisdompark.enums.SubsidyType;
import com.eco.wisdompark.mapper.DeptMapper;
import com.eco.wisdompark.mapper.SubsidyRecordMapper;
import com.eco.wisdompark.mapper.SubsidyRuleMapper;
import com.eco.wisdompark.mapper.UserMapper;
import com.eco.wisdompark.service.SubsidyRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import java.util.ArrayList;

/**
 * <p>
 * CPU卡-补助记录表 服务实现类
 * </p>
 *
 * @author litao
 * @since 2018-12-28
 */
@Service
public class SubsidyRecordServiceImpl extends ServiceImpl<SubsidyRecordMapper,
        SubsidyRecord> implements SubsidyRecordService {

    @Autowired
    private SubsidyRuleMapper subsidyRuleMapper;
    @Autowired
    private SubsidyRecordMapper subsidyRecordMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private DeptMapper deptMapper;

    @Override
    public SubsidyDetailsDto searchAutoSubsidyRecord(SearchAutoSubsidyRecordReq searchAutoSubsidyRecordReq) {
        SubsidyDetailsDto subsidyDetailsDto = new SubsidyDetailsDto();
        SubsidyRule subsidyRule = subsidyRuleMapper.selectById(searchAutoSubsidyRecordReq.getRuleId());
        List<User> users = selectUsersByDeptId(subsidyRule.getDeptId());
        if (CollectionUtils.isNotEmpty(users)) {
            List<Integer> userIds = users.stream()
                    .map(user -> user.getId())
                    .collect(Collectors.toList());
            Map<Integer, User> userMap = users.stream()
                    .collect(Collectors.toMap(User::getId, a -> a, (k1, k2) -> k1));
            List<SubsidyRecord> selectByUsersAndDate = selectByUsersAndDate(userIds,
                    searchAutoSubsidyRecordReq.getSubsidyTime());
            List<SubsidyRecordListRespDto> subsidyRecordListRespDtos = converter(userMap, selectByUsersAndDate);
            subsidyDetailsDto.setDeptName(getDeptName(subsidyRule.getDeptId()));
            subsidyDetailsDto.setSubsidyRecords(subsidyRecordListRespDtos);
            if (CollectionUtils.isNotEmpty(selectByUsersAndDate)) {
                subsidyDetailsDto.setSubsidyAmount(selectByUsersAndDate.get(0).getAmount()
                        .multiply(new BigDecimal(selectByUsersAndDate.size())));
                subsidyDetailsDto.setSubsidyTime(selectByUsersAndDate.get(0).getCreateTime());
            }
        }
        return subsidyDetailsDto;
    }

    /**
     * 根据人员ID获取CPU卡
     *
     * @return 补助规则
     */
    private List<User> selectUsersByDeptId(Integer deptId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("dept_id", deptId);
        return userMapper.selectList(queryWrapper);
    }

    /**
     * 根据人员ID获取自动补助记录
     *
     * @param userIds     用户ID
     * @param subsidyTime 补助时间
     * @return 自动补助记录
     */
    private List<SubsidyRecord> selectByUsersAndDate(List<Integer> userIds, LocalDate subsidyTime) {
        LocalDateTime start = LocalDateTime.of(subsidyTime, LocalTime.MIN);
        LocalDateTime end = LocalDateTime.of(subsidyTime, LocalTime.MAX);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.in("user_id", userIds);
        queryWrapper.gt("create_time", start);
        queryWrapper.lt("create_time", end);
        queryWrapper.eq("type", SubsidyType.AUTOMATIC.getCode());
        return subsidyRecordMapper.selectList(queryWrapper);
    }

    /**
     * 消费记录转换
     *
     * @param userMap        用户信息
     * @param subsidyRecords 消费记录
     * @return 消费记录
     */
    private List<SubsidyRecordListRespDto> converter(Map<Integer, User> userMap, List<SubsidyRecord> subsidyRecords) {
        return subsidyRecords.stream().map(subsidyRecord -> {
            SubsidyRecordListRespDto subsidyRecordListRespDto = new SubsidyRecordListRespDto();
            User user = userMap.get(subsidyRecord.getUserId());
            subsidyRecordListRespDto.setUserName(user.getUserName());
            subsidyRecordListRespDto.setUserCardNum(user.getUserCardNum());
            subsidyRecordListRespDto.setCardSerialNo(subsidyRecord.getCardSerialNo());
            subsidyRecordListRespDto.setSubsidyAmount(subsidyRecord.getAmount());
            return subsidyRecordListRespDto;
        }).collect(Collectors.toList());
    }

    @Override
    public IPage<SubsidyRecordDto> searchUserSubsidyRecordDtos(SearchConsumeRecordDto searchConsumeRecordDto) {
        IPage<SubsidyRecordDto> result = new Page<>();
        QueryWrapper<SubsidyRecord> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", searchConsumeRecordDto.getId());
        if (org.apache.commons.lang3.StringUtils.isNotBlank(searchConsumeRecordDto.getStartTime())) {
            wrapper.ge("create_time", LocalDateTimeUtils.localTime(searchConsumeRecordDto.getStartTime()));
        }
        if (org.apache.commons.lang3.StringUtils.isNotBlank(searchConsumeRecordDto.getEndTime())) {
            wrapper.le("create_time", LocalDateTimeUtils.localTime(searchConsumeRecordDto.getEndTime()));
        }
        IPage<SubsidyRecord> page = baseMapper.selectPage(new Page<>(searchConsumeRecordDto.getCurrentPage(), searchConsumeRecordDto.getPageSize()), wrapper);
        result.setPages(page.getPages());
        result.setCurrent(page.getCurrent());
        result.setSize(page.getSize());
        result.setTotal(page.getTotal());
        List<SubsidyRecord> list = page.getRecords();
        if (!list.isEmpty()) {
            List<SubsidyRecordDto> dtoList = new ArrayList<>();
            list.forEach(e -> {
                SubsidyRecordDto dto = new SubsidyRecordDto();
                BeanUtils.copyProperties(e, dto);
                dto.setCreateTime(LocalDateTimeUtils.localTimeStr(e.getCreateTime()));
                dtoList.add(dto);
            });
            result.setRecords(dtoList);
        }
        return result;
    }

    @Override
    public IPage<ManualSubsidyRecordListRespDto> searchManualSubsidyRecord(PageReqDto<Integer> pageReqDto) {
        IPage page = PageReqDtoToPageConverter.converter(pageReqDto);
        List<ManualSubsidyRecordListRespDto> subsidyRecordListRespDtos = Lists.newArrayList();
        IPage<SubsidyRecord> subsidyRecordPage = null;
        if (Objects.isNull(pageReqDto.getQuery())) {
            subsidyRecordPage = selectByUsers(null, page);
            List<SubsidyRecord> subsidyRecords = subsidyRecordPage.getRecords();
            List<Integer> userIds = subsidyRecords.stream()
                    .map(subsidyRecord -> subsidyRecord.getUserId())
                    .collect(Collectors.toList());
            List<User> users = selectUsersBySubsidyRecordId(userIds);
            if (CollectionUtils.isNotEmpty(users)) {
                Map<Integer, User> userMap = users.stream()
                        .collect(Collectors.toMap(User::getId, a -> a, (k1, k2) -> k1));
                subsidyRecordListRespDtos = manualConverter(null, userMap, subsidyRecordPage.getRecords());
            }
        } else {
            List<User> users = selectUsersByDeptId(pageReqDto.getQuery());
            if (CollectionUtils.isNotEmpty(users)) {
                List<Integer> userIds = users.stream()
                        .map(user -> user.getId())
                        .collect(Collectors.toList());
                Map<Integer, User> userMap = users.stream()
                        .collect(Collectors.toMap(User::getId, a -> a, (k1, k2) -> k1));
                subsidyRecordPage = selectByUsers(userIds, page);
                subsidyRecordListRespDtos = manualConverter(pageReqDto.getQuery(),
                        userMap, subsidyRecordPage.getRecords());
            }
        }
        IPage<ManualSubsidyRecordListRespDto> result = new Page<>();
        result.setPages(subsidyRecordPage.getPages());
        result.setCurrent(subsidyRecordPage.getCurrent());
        result.setSize(subsidyRecordPage.getSize());
        result.setTotal(subsidyRecordPage.getTotal());
        result.setRecords(subsidyRecordListRespDtos);
        return result;
    }

    /**
     * 消费记录转换
     *
     * @param userMap        用户信息
     * @param subsidyRecords 消费记录
     * @return 消费记录
     */
    private List<ManualSubsidyRecordListRespDto> manualConverter(Integer deptId, Map<Integer, User> userMap,
                                                                 List<SubsidyRecord> subsidyRecords) {
        return subsidyRecords.stream().map(subsidyRecord -> {
            ManualSubsidyRecordListRespDto manualSubsidyRecordListRespDto = new ManualSubsidyRecordListRespDto();
            User user = userMap.get(subsidyRecord.getUserId());
            if (Objects.isNull(deptId)) {
                manualSubsidyRecordListRespDto.setDeptName(getDeptName(user.getDeptId()));
            } else {
                manualSubsidyRecordListRespDto.setDeptName(getDeptName(deptId));
            }
            manualSubsidyRecordListRespDto.setUserName(user.getUserName());
            manualSubsidyRecordListRespDto.setUserCardNum(user.getUserCardNum());
            manualSubsidyRecordListRespDto.setCardSerialNo(subsidyRecord.getCardSerialNo());
            manualSubsidyRecordListRespDto.setSubsidyAmount(subsidyRecord.getAmount());
            return manualSubsidyRecordListRespDto;
        }).collect(Collectors.toList());
    }

    /**
     * 获取上下级部门名称
     *
     * @param deptId 部门ID
     * @return 上下级部门名称
     */
    private String getDeptName(Integer deptId) {
        Dept dept = deptMapper.selectById(deptId);
        Dept parent = deptMapper.selectById(dept.getDeptUpId());
        return parent.getDeptName() + "/" + dept.getDeptName();
    }

    /**
     * 根据人员ID获取手动补助记录
     *
     * @param userIds 用户ID
     * @return 手动补助记录selectByUsers
     */
    private IPage<SubsidyRecord> selectByUsers(List<Integer> userIds, IPage page) {
        QueryWrapper queryWrapper = new QueryWrapper();
        if (CollectionUtils.isNotEmpty(userIds)) {
            queryWrapper.in("user_id", userIds);
        }
        queryWrapper.eq("type", SubsidyType.MANUAL.getCode());
        queryWrapper.orderByDesc("create_time");
        return subsidyRecordMapper.selectPage(page, queryWrapper);
    }

    /**
     * 根据消费记录ID获取人员信息
     *
     * @param subsidyRecordId 消费记录ID
     * @return 人员信息
     */
    private List<User> selectUsersBySubsidyRecordId(List<Integer> subsidyRecordId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.in("id", subsidyRecordId);
        return userMapper.selectList(queryWrapper);
    }

}