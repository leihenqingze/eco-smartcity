package com.eco.wisdompark.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eco.wisdompark.common.dto.ResponseData;
import com.eco.wisdompark.common.exceptions.WisdomParkException;
import com.eco.wisdompark.common.utils.ExcelUtil;
import com.eco.wisdompark.common.utils.LocalDateTimeUtils;
import com.eco.wisdompark.converter.req.PageReqDtoToPageConverter;
import com.eco.wisdompark.domain.dto.req.PageReqDto;
import com.eco.wisdompark.domain.dto.req.consumeRecord.SearchConsumeRecordDto;
import com.eco.wisdompark.domain.dto.req.subsidy.SearchAutoSubsidyRecordReq;
import com.eco.wisdompark.domain.dto.req.subsidyRecord.SearchSubsidyRecordDto;
import com.eco.wisdompark.domain.dto.req.subsidyRecord.SubsidyRecordDto;
import com.eco.wisdompark.domain.dto.req.user.SearchUserDto;
import com.eco.wisdompark.domain.dto.resp.ManualSubsidyRecordListRespDto;
import com.eco.wisdompark.domain.dto.resp.SubsidyDetailsDto;
import com.eco.wisdompark.domain.dto.resp.SubsidyRecordListRespDto;
import com.eco.wisdompark.domain.model.*;
import com.eco.wisdompark.enums.SubsidyType;
import com.eco.wisdompark.mapper.DeptMapper;
import com.eco.wisdompark.mapper.SubsidyRecordMapper;
import com.eco.wisdompark.mapper.SubsidyRuleMapper;
import com.eco.wisdompark.mapper.UserMapper;
import com.eco.wisdompark.service.DeptService;
import com.eco.wisdompark.service.SubsidyRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eco.wisdompark.service.UserService;
import com.google.common.collect.Lists;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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
    @Autowired
    private UserService userService;
    @Autowired
    private DeptService deptService;

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
            List<SubsidyRecord> selectByUsersAndDate = selectByUsersAndDate(userIds);
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
     * @param userIds 用户ID
     * @return 自动补助记录
     */
    private List<SubsidyRecord> selectByUsersAndDate(List<Integer> userIds) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime start = LocalDateTime.of(now.getYear(), now.getMonth(), 1, 0, 0, 0);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.in("user_id", userIds);
        queryWrapper.ge("create_time", start);
        queryWrapper.le("create_time", now);
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

    public IPage<SubsidyRecordDto> searchSubsidyRecordDtos(SearchSubsidyRecordDto searchSubsidyRecordDto) {
        IPage<SubsidyRecordDto> result = new Page<>();
        IPage<SubsidyRecord> page = baseMapper.selectPage(new Page<>(searchSubsidyRecordDto.getCurrentPage(),
                searchSubsidyRecordDto.getPageSize()), getWhere(searchSubsidyRecordDto));
        result.setPages(page.getPages());
        result.setCurrent(page.getCurrent());
        result.setSize(page.getSize());
        result.setTotal(page.getTotal());
        List<SubsidyRecord> list = page.getRecords();
        if (!list.isEmpty()) {
            List<SubsidyRecordDto> dtoList = new ArrayList<>();
            packageRechargeRecordDtoList(list, dtoList);
            result.setRecords(dtoList);
        }
        return result;
    }

    private void packageRechargeRecordDtoList(List<SubsidyRecord> list, List<SubsidyRecordDto> dtoList) {
        list.forEach(e -> {
            SubsidyRecordDto dto = new SubsidyRecordDto();
            BeanUtils.copyProperties(e, dto);
            dto.setCreateTime(LocalDateTimeUtils.localTimeStr(e.getCreateTime()));
            User user = userService.getById(e.getUserId());
            if (user != null) {
                dto.setUserName(user.getUserName());
                dto.setPhone(user.getPhoneNum());
                Dept dept = deptService.getById(user.getDeptId());
                if (dept != null) {
                    dto.setDeptName(dept.getDeptName());
                }
            }
            dtoList.add(dto);
        });
    }

    private QueryWrapper<SubsidyRecord> getWhere(SearchSubsidyRecordDto searchSubsidyRecordDto) {
        QueryWrapper<SubsidyRecord> wrapper = new QueryWrapper<>();
        List<Integer> userIds = getUserIds(searchSubsidyRecordDto);
        if (!org.springframework.util.CollectionUtils.isEmpty(userIds)) {
            wrapper.in("user_id", userIds);
        }
        if (!StringUtils.isEmpty(searchSubsidyRecordDto.getStartTime())) {
            wrapper.ge("create_time", LocalDateTimeUtils.localTime(searchSubsidyRecordDto.getStartTime()));
        }
        if (!StringUtils.isEmpty(searchSubsidyRecordDto.getEndTime())) {
            wrapper.le("create_time", LocalDateTimeUtils.localTime(searchSubsidyRecordDto.getEndTime()));
        }
        if (!StringUtils.isEmpty(searchSubsidyRecordDto.getCard_serialNo())) {
            wrapper.eq("card_serialNo", searchSubsidyRecordDto.getCard_serialNo());
        }
        wrapper.orderByDesc("create_time");
        return wrapper;
    }

    private List<Integer> getUserIds(SearchSubsidyRecordDto searchSubsidyRecordDto) {
        if (!StringUtils.isEmpty(searchSubsidyRecordDto.getUserName())
                || !StringUtils.isEmpty(searchSubsidyRecordDto.getPhone())
                || searchSubsidyRecordDto.getDeptId() != null) {
            SearchUserDto searchUserDto = new SearchUserDto();
            searchUserDto.setUserName(searchSubsidyRecordDto.getUserName());
            searchUserDto.setPhoneNum(searchSubsidyRecordDto.getPhone());
            searchUserDto.setDeptId(searchSubsidyRecordDto.getDeptId());
            List<User> userList = userService.getListByQuery(searchUserDto);
            return userList.stream().map(User::getId).collect(Collectors.toList());
        }
        return Collections.EMPTY_LIST;
    }

    public void exportSearchSubsidyRecord(SearchSubsidyRecordDto searchSubsidyRecordDto, HttpServletResponse response) {
        List<SubsidyRecordDto> rechargeRecordDtoList = Lists.newArrayList();
        List<SubsidyRecord> rechargeRecords = baseMapper.selectList(getWhere(searchSubsidyRecordDto));
        if (!rechargeRecords.isEmpty()) {
            packageRechargeRecordDtoList(rechargeRecords, rechargeRecordDtoList);
        }
        exportExcel(rechargeRecordDtoList, response);
    }

    private void exportExcel(List<SubsidyRecordDto> subsidyRecordDtos, HttpServletResponse response) {
        //excel标题
        String[] title = {"卡面序列号", "姓名", "部门名称", "手机号", "补助金额", "补助类型", "补助时间"};
        //excel文件名
        String fileName = "subsidy_record_" + System.currentTimeMillis() + ".xls";
        //sheet名
        String sheetName = "补助明细";
        String[][] content = new String[subsidyRecordDtos.size()][];
        if (!org.springframework.util.CollectionUtils.isEmpty(subsidyRecordDtos)) {
            for (int i = 0; i < subsidyRecordDtos.size(); i++) {
                content[i] = new String[title.length];
                SubsidyRecordDto obj = subsidyRecordDtos.get(i);
                content[i][0] = obj.getCardSerialNo();
                content[i][1] = obj.getUserName();
                content[i][2] = obj.getDeptName();
                content[i][3] = obj.getPhone();
                content[i][4] = obj.getAmount().toString();
                content[i][5] = SubsidyType.valueOf(obj.getType())
                        .getDescription();
                content[i][6] = obj.getCreateTime();
            }
        }

        //创建HSSFWorkbook
        Map<Integer, Integer> amountColMap = new HashMap<>();
        amountColMap.put(4, 4);
        HSSFWorkbook wb = ExcelUtil.getHSSFWorkbook(sheetName, title, content, null, amountColMap);
        //响应到客户端
        try {
            ExcelUtil.setResponseHeader(response, fileName);
            OutputStream os = response.getOutputStream();
            wb.write(os);
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new WisdomParkException(ResponseData.STATUS_CODE_615, "下载失败");
        }
    }

    public Double countSubsidyRecord(SearchSubsidyRecordDto searchSubsidyRecordDto) {
        return subsidyRecordMapper.countAmount(getWhere(searchSubsidyRecordDto));
    }

}