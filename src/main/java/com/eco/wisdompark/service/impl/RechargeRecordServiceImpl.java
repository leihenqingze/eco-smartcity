package com.eco.wisdompark.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eco.wisdompark.common.utils.LocalDateTimeUtils;
import com.eco.wisdompark.domain.dto.inner.InnerCpuCardInfoDto;
import com.eco.wisdompark.domain.dto.req.consumeRecord.ConsumeRecordDto;
import com.eco.wisdompark.domain.dto.req.consumeRecord.SearchConsumeRecordDto;
import com.eco.wisdompark.domain.dto.req.rechargeRecord.RechargeRecordDto;
import com.eco.wisdompark.domain.model.ConsumeRecord;
import com.eco.wisdompark.domain.model.RechargeRecord;
import com.eco.wisdompark.enums.RechargeType;
import com.eco.wisdompark.mapper.RechargeRecordMapper;
import com.eco.wisdompark.service.RechargeRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public boolean saveRechargeRecord(InnerCpuCardInfoDto cardInfoDto, BigDecimal amount,
                                      RechargeType rechargeType, String importSerialNo) {
        RechargeRecord rechargeRecord = new RechargeRecord();
        rechargeRecord.setCardId(cardInfoDto.getCardId());
        rechargeRecord.setCardSerialNo(cardInfoDto.getCardSerialNo());
        rechargeRecord.setAmount(amount);
        rechargeRecord.setRechargeType(rechargeType.getCode());
        if (!StringUtils.isEmpty(importSerialNo)){
            rechargeRecord.setImportSerialno(importSerialNo);
        }
        rechargeRecord.setUserId(cardInfoDto.getUserId());
        rechargeRecord.setCreateTime(LocalDateTime.now());
        return save(rechargeRecord);
    }

    @Override
    public IPage<RechargeRecordDto> searchUserRechargeRecordDtos(SearchConsumeRecordDto searchConsumeRecordDto) {
        IPage<RechargeRecordDto> result=new Page<>();
        QueryWrapper<RechargeRecord> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",searchConsumeRecordDto.getId());
        if(org.apache.commons.lang3.StringUtils.isNotBlank(searchConsumeRecordDto.getStartTime())){
            wrapper.ge("create_time", LocalDateTimeUtils.localTime(searchConsumeRecordDto.getStartTime()));
        }
        if(org.apache.commons.lang3.StringUtils.isNotBlank(searchConsumeRecordDto.getEndTime())){
            wrapper.le("create_time", LocalDateTimeUtils.localTime(searchConsumeRecordDto.getEndTime()));
        }
        IPage<RechargeRecord> page = baseMapper.selectPage(new Page<>(searchConsumeRecordDto.getCurrentPage(), searchConsumeRecordDto.getPageSize()), wrapper);
        result.setPages(page.getPages());
        result.setCurrent(page.getCurrent());
        result.setSize(page.getSize());
        result.setTotal(page.getTotal());
        List<RechargeRecord> list = page.getRecords();
        if(!list.isEmpty()){
            List<RechargeRecordDto> dtoList = new ArrayList<>();
            list.forEach(e->{
                RechargeRecordDto dto=new RechargeRecordDto();
                BeanUtils.copyProperties(e, dto);
                dtoList.add(dto);
            });
            result.setRecords(dtoList);
        }
        return result;
    }

}
