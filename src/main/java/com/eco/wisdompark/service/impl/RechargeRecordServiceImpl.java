package com.eco.wisdompark.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eco.wisdompark.common.dto.ResponseData;
import com.eco.wisdompark.common.exceptions.WisdomParkException;
import com.eco.wisdompark.common.utils.ConsumeIdentityUtils;
import com.eco.wisdompark.common.utils.ExcelUtil;
import com.eco.wisdompark.common.utils.LocalDateTimeUtils;
import com.eco.wisdompark.domain.dto.inner.InnerCpuCardInfoDto;
import com.eco.wisdompark.domain.dto.req.rechargeRecord.RechargeRecordDto;
import com.eco.wisdompark.domain.dto.req.rechargeRecord.SearchRechargeRecordDto;
import com.eco.wisdompark.domain.dto.req.user.SearchUserDto;
import com.eco.wisdompark.domain.model.*;
import com.eco.wisdompark.enums.RechargeType;
import com.eco.wisdompark.enums.RechargeWay;
import com.eco.wisdompark.mapper.RechargeRecordMapper;
import com.eco.wisdompark.service.DeptService;
import com.eco.wisdompark.service.RechargeRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eco.wisdompark.service.UserService;
import com.google.common.collect.Lists;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * CPU卡-充值记录表 服务实现类
 * </p>
 *
 * @author litao
 * @since 2018-12-28
 */
@Service
public class RechargeRecordServiceImpl extends ServiceImpl<RechargeRecordMapper, RechargeRecord> implements RechargeRecordService {

    @Autowired
    private UserService userService;

    @Autowired
    private DeptService deptService;

    @Autowired
    private RechargeRecordMapper rechargeRecordMapper;

    @Override
    public boolean saveRechargeRecord(InnerCpuCardInfoDto cardInfoDto, BigDecimal amount,BigDecimal rechargeAgoAmount,BigDecimal rechargeAfterAmount,
                                      RechargeType rechargeType, String importSerialNo,int rechargeWay) {
        RechargeRecord rechargeRecord = new RechargeRecord();
        rechargeRecord.setCardId(cardInfoDto.getCardId());
        rechargeRecord.setCardSerialNo(cardInfoDto.getCardSerialNo());
        rechargeRecord.setAmount(amount);
        rechargeRecord.setRechargeAgoAmount(rechargeAgoAmount);
        rechargeRecord.setRechargeAfterAmount(rechargeAfterAmount);
        rechargeRecord.setRechargeType(rechargeType.getCode());
        rechargeRecord.setRechargeWay(rechargeWay);
        if (!StringUtils.isEmpty(importSerialNo)){
            rechargeRecord.setImportSerialno(importSerialNo);
        }
        rechargeRecord.setUserId(cardInfoDto.getUserId());
        rechargeRecord.setCreateTime(LocalDateTime.now());
        return save(rechargeRecord);
    }

    @Override
    public IPage<RechargeRecordDto> searchUserRechargeRecordDtos(SearchRechargeRecordDto searchRechargeRecordDto) {
        IPage<RechargeRecordDto> result=new Page<>();
        QueryWrapper<RechargeRecord> wrapper = new QueryWrapper<>();

        List<Integer> userIdList = Lists.newArrayList();

        if(!StringUtils.isEmpty(searchRechargeRecordDto.getUserName())
                || !StringUtils.isEmpty(searchRechargeRecordDto.getPhone())
                || searchRechargeRecordDto.getDeptId() != null){
            SearchUserDto searchUserDto = new SearchUserDto();
            searchUserDto.setUserName(searchRechargeRecordDto.getUserName());
            searchUserDto.setPhoneNum(searchRechargeRecordDto.getPhone());
            searchUserDto.setDeptId(searchRechargeRecordDto.getDeptId());

            List<User> userList = userService.getListByQuery(searchUserDto);
            if(!CollectionUtils.isEmpty(userList)){
                userList.forEach(e -> {
                    userIdList.add(e.getId());
                });
            }
            if(CollectionUtils.isEmpty(userIdList)){
                return result;
            }
        }

        if(!CollectionUtils.isEmpty(userIdList)){
            wrapper.in("user_id",userIdList);
        }
        if(!StringUtils.isEmpty(searchRechargeRecordDto.getStartTime())){
            wrapper.ge("create_time", LocalDateTimeUtils.localTime(searchRechargeRecordDto.getStartTime()));
        }
        if(!StringUtils.isEmpty(searchRechargeRecordDto.getEndTime())){
            wrapper.le("create_time", LocalDateTimeUtils.localTime(searchRechargeRecordDto.getEndTime()));
        }
        if(!StringUtils.isEmpty(searchRechargeRecordDto.getCard_serialNo())){
            wrapper.eq("card_serialNo",searchRechargeRecordDto.getCard_serialNo());
        }
        wrapper.orderByDesc("create_time");
        IPage<RechargeRecord> page = baseMapper.selectPage(new Page<>(searchRechargeRecordDto.getCurrentPage(), searchRechargeRecordDto.getPageSize()), wrapper);
        result.setPages(page.getPages());
        result.setCurrent(page.getCurrent());
        result.setSize(page.getSize());
        result.setTotal(page.getTotal());
        List<RechargeRecord> list = page.getRecords();
        if(!list.isEmpty()){
            List<RechargeRecordDto> dtoList = new ArrayList<>();
            packageRechargeRecordDtoList(list,dtoList);
            result.setRecords(dtoList);
        }
        return result;
    }

    @Override
    public BigDecimal totalRechargeAmount(SearchRechargeRecordDto searchRechargeRecordDto) {

        BigDecimal totalRechargeAmount = BigDecimal.ZERO;
        List<Integer> userIdList = Lists.newArrayList();

        if(!StringUtils.isEmpty(searchRechargeRecordDto.getUserName())
                || !StringUtils.isEmpty(searchRechargeRecordDto.getPhone())
                || searchRechargeRecordDto.getDeptId() != null){
            SearchUserDto searchUserDto = new SearchUserDto();
            searchUserDto.setUserName(searchRechargeRecordDto.getUserName());
            searchUserDto.setPhoneNum(searchRechargeRecordDto.getPhone());
            searchUserDto.setDeptId(searchRechargeRecordDto.getDeptId());

            List<User> userList = userService.getListByQuery(searchUserDto);
            if(!CollectionUtils.isEmpty(userList)){
                userList.forEach(e -> {
                    userIdList.add(e.getId());
                });
            }
            if(CollectionUtils.isEmpty(userIdList)){
                return totalRechargeAmount;
            }
        }

        Map paramMap = new HashMap();
        paramMap.put("userIdList",userIdList);
        paramMap.put("startTime",searchRechargeRecordDto.getStartTime());
        paramMap.put("endTime",searchRechargeRecordDto.getEndTime());
        paramMap.put("cardSerialNo",searchRechargeRecordDto.getCard_serialNo());

        return rechargeRecordMapper.totalRechargeAmount(paramMap);
    }

    @Override
    public void exportShopPosConsumeRecordDtos(SearchRechargeRecordDto searchRechargeRecordDto, HttpServletResponse response) {
        List<RechargeRecordDto> rechargeRecordDtoList = Lists.newArrayList();
        QueryWrapper<RechargeRecord> wrapper = new QueryWrapper<>();

        List<Integer> userIdList = Lists.newArrayList();

        if(!StringUtils.isEmpty(searchRechargeRecordDto.getUserName())
                || !StringUtils.isEmpty(searchRechargeRecordDto.getPhone())
                || searchRechargeRecordDto.getDeptId() != null){
            SearchUserDto searchUserDto = new SearchUserDto();
            searchUserDto.setUserName(searchRechargeRecordDto.getUserName());
            searchUserDto.setPhoneNum(searchRechargeRecordDto.getPhone());
            searchUserDto.setDeptId(searchRechargeRecordDto.getDeptId());

            List<User> userList = userService.getListByQuery(searchUserDto);
            if(!CollectionUtils.isEmpty(userList)){
                userList.forEach(e -> {
                    userIdList.add(e.getId());
                });
            }
            if(CollectionUtils.isEmpty(userIdList)){
                // 直接导出
                exportExcel(rechargeRecordDtoList,response);
                return;
            }
        }

        if(!CollectionUtils.isEmpty(userIdList)){
            wrapper.in("user_id",userIdList);
        }
        if(!StringUtils.isEmpty(searchRechargeRecordDto.getStartTime())){
            wrapper.ge("create_time", LocalDateTimeUtils.localTime(searchRechargeRecordDto.getStartTime()));
        }
        if(!StringUtils.isEmpty(searchRechargeRecordDto.getEndTime())){
            wrapper.le("create_time", LocalDateTimeUtils.localTime(searchRechargeRecordDto.getEndTime()));
        }
        if(!StringUtils.isEmpty(searchRechargeRecordDto.getCard_serialNo())){
            wrapper.eq("card_serialNo",searchRechargeRecordDto.getCard_serialNo());
        }
        List<RechargeRecord> rechargeRecords = baseMapper.selectList(wrapper);
        if(!rechargeRecords.isEmpty()){
            packageRechargeRecordDtoList(rechargeRecords,rechargeRecordDtoList);
        }
        exportExcel(rechargeRecordDtoList,response);

    }

    private void exportExcel(List<RechargeRecordDto> rechargeRecordDtoList,HttpServletResponse response){
        //excel标题
        String[] title = {"卡面序列号","姓名","部门名称","充值金额","充值前总金额","充值后总金额","充值类型","交易时间","交易类型","身份信息"};

        //excel文件名
        String fileName = "recharge_record_"+System.currentTimeMillis()+".xls";

        //sheet名
        String sheetName = "充值明细";

        String [][] content = new String[rechargeRecordDtoList.size()][];

        if(!CollectionUtils.isEmpty(rechargeRecordDtoList)){
            for (int i = 0; i < rechargeRecordDtoList.size(); i++) {
                content[i] = new String[title.length];
                RechargeRecordDto obj = rechargeRecordDtoList.get(i);
                content[i][0] = obj.getCardSerialNo();
                content[i][1] = obj.getUserName();
                content[i][2] = obj.getDeptName();
                content[i][3] = obj.getAmount().toString();
                content[i][4] = (obj.getRechargeAgoAmount()!= null?obj.getRechargeAgoAmount():BigDecimal.ZERO).toString();
                content[i][5] = (obj.getRechargeAfterAmount()!= null?obj.getRechargeAfterAmount():BigDecimal.ZERO).toString();
                content[i][6] = obj.getRechargeWay();
                content[i][7] = obj.getCreateTime();
                content[i][8] = "充值";
                content[i][9] = obj.getConsumeIdentity();
            }
        }

        //创建HSSFWorkbook
        Map<Integer,Integer> amountColMap = new HashMap<>();
        amountColMap.put(3,3);
        amountColMap.put(4,4);
        amountColMap.put(5,5);
        HSSFWorkbook wb = ExcelUtil.getHSSFWorkbook(sheetName, title, content, null,amountColMap);

        //响应到客户端
        try {
            ExcelUtil.setResponseHeader(response, fileName);
            OutputStream os = response.getOutputStream();
            wb.write(os);
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new WisdomParkException(ResponseData.STATUS_CODE_615,"下载失败");
        }
    }

    private void packageRechargeRecordDtoList(List<RechargeRecord> list,List<RechargeRecordDto> dtoList){
        list.forEach(e->{
            RechargeRecordDto dto=new RechargeRecordDto();
            BeanUtils.copyProperties(e, dto);
            dto.setCreateTime(LocalDateTimeUtils.localTimeStr(e.getCreateTime()));
            dto.setRechargeType(RechargeType.valueOf(e.getRechargeType()).getDescription());
            dto.setRechargeWay(RechargeWay.valueOf(e.getRechargeWay()).getDescription());
            User user = userService.getById(e.getUserId());
            if(user != null){
                dto.setUserName(user.getUserName());
                dto.setPhone(user.getPhoneNum());
                Dept dept = deptService.getById(user.getDeptId());
                if(dept != null){
                    dto.setDeptName(dept.getDeptName());
                    dto.setConsumeIdentity(ConsumeIdentityUtils.getConsumeIdentityUtils(dept.getConsumeIdentity()));
                }
            }
            dtoList.add(dto);
        });
    }

}
