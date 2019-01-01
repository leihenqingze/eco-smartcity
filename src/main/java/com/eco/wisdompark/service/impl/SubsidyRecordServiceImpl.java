package com.eco.wisdompark.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eco.wisdompark.common.utils.LocalDateTimeUtils;
import com.eco.wisdompark.domain.dto.req.consumeRecord.SearchConsumeRecordDto;
import com.eco.wisdompark.domain.dto.req.subsidy.SearchAutoSubsidyRecordReq;
import com.eco.wisdompark.domain.dto.req.subsidyRecord.SubsidyRecordDto;
import com.eco.wisdompark.domain.dto.resp.SubsidyDetailsDto;
import com.eco.wisdompark.domain.dto.resp.SubsidyRecordListRespDto;
import com.eco.wisdompark.domain.model.Dept;
import com.eco.wisdompark.domain.model.SubsidyRecord;
import com.eco.wisdompark.domain.model.SubsidyRule;
import com.eco.wisdompark.domain.model.User;
import com.eco.wisdompark.mapper.DeptMapper;
import com.eco.wisdompark.mapper.SubsidyRecordMapper;
import com.eco.wisdompark.mapper.SubsidyRuleMapper;
import com.eco.wisdompark.mapper.UserMapper;
import com.eco.wisdompark.service.SubsidyRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
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
    public SubsidyDetailsDto searchDeptSubsidyRecord(SearchAutoSubsidyRecordReq searchAutoSubsidyRecordReq) {
        SubsidyDetailsDto subsidyDetailsDto = new SubsidyDetailsDto();
        SubsidyRule subsidyRule = subsidyRuleMapper.selectById(searchAutoSubsidyRecordReq.getRuleId());
        List<User> users = selectUsersByDeptId(subsidyRule.getDeptId());
        List<Integer> userIds = users.stream()
                .map(user -> user.getId())
                .collect(Collectors.toList());
        List<SubsidyRecord> selectByUsersAndDate = selectByUsersAndDate(userIds,
                searchAutoSubsidyRecordReq.getSubsidyTime());
        Map<Integer, SubsidyRecord> subsidyRecordMap = selectByUsersAndDate.stream()
                .collect(Collectors.toMap(SubsidyRecord::getUserId, a -> a, (k1, k2) -> k1));
        List<SubsidyRecordListRespDto> subsidyRecordListRespDtos = users.stream().map(user -> {
            SubsidyRecordListRespDto subsidyRecordListRespDto = new SubsidyRecordListRespDto();
            subsidyRecordListRespDto.setUserName(user.getUserName());
            subsidyRecordListRespDto.setUserCardNum(user.getUserCardNum());
            if (subsidyRecordMap.containsKey(user.getId())) {
                SubsidyRecord subsidyRecord = subsidyRecordMap.get(user.getId());
                subsidyRecordListRespDto.setCardSerialNo(subsidyRecord.getCardSerialNo());
                subsidyRecordListRespDto.setSubsidyAmount(subsidyRecord.getAmount());
                return subsidyRecordListRespDto;
            }
            return null;
        }).collect(Collectors.toList());
        subsidyDetailsDto.setDeptName(getDeptName(subsidyRule.getDeptId()));
        subsidyDetailsDto.setSubsidyRecords(subsidyRecordListRespDtos);
        if (CollectionUtils.isNotEmpty(selectByUsersAndDate)) {
            subsidyDetailsDto.setSubsidyAmount(selectByUsersAndDate.get(0).getAmount()
                    .multiply(new BigDecimal(selectByUsersAndDate.size())));
            subsidyDetailsDto.setSubsidyTime(selectByUsersAndDate.get(0).getCreateTime());
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
                dtoList.add(dto);
            });
            result.setRecords(dtoList);
        }
        return result;
    }

    /**
     * 根据人员ID获取CPU卡
     *
     * @return 补助规则
     */
    private List<SubsidyRecord> selectByUsersAndDate(List<Integer> userIds, LocalDate subsidyTime) {
        LocalDateTime start = LocalDateTime.of(subsidyTime, LocalTime.MIN);
        LocalDateTime end = LocalDateTime.of(subsidyTime, LocalTime.MAX);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.in("user_id", userIds);
        queryWrapper.gt("create_time", start);
        queryWrapper.lt("create_time", end);
        return subsidyRecordMapper.selectList(queryWrapper);
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

}