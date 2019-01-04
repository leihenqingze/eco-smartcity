package com.eco.wisdompark.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eco.wisdompark.common.utils.LocalDateTimeUtils;
import com.eco.wisdompark.domain.dto.req.consumeRecord.ConsumeRecordDto;
import com.eco.wisdompark.domain.dto.req.consumeRecord.SearchConsumeRecordDto;
import com.eco.wisdompark.domain.model.ConsumeRecord;
import com.eco.wisdompark.domain.model.User;
import com.eco.wisdompark.mapper.ConsumeRecordMapper;
import com.eco.wisdompark.service.ConsumeRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eco.wisdompark.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * CPU卡-消费记录表 服务实现类
 * </p>
 *
 * @author litao
 * @since 2018-12-28
 */
@Service
public class ConsumeRecordServiceImpl extends ServiceImpl<ConsumeRecordMapper, ConsumeRecord> implements ConsumeRecordService {

    @Autowired
    private UserService userService;

    @Override
    public IPage<ConsumeRecordDto> searchUserConsumeRecordDtos(SearchConsumeRecordDto searchConsumeRecordDto) {
        IPage<ConsumeRecordDto> result=new Page<>();
        QueryWrapper<ConsumeRecord> wrapper = new QueryWrapper<ConsumeRecord>();
        wrapper.eq("user_id",searchConsumeRecordDto.getId());
        if(StringUtils.isNotBlank(searchConsumeRecordDto.getStartTime())){
            wrapper.ge("create_time", LocalDateTimeUtils.localTime(searchConsumeRecordDto.getStartTime()));
        }
        if(StringUtils.isNotBlank(searchConsumeRecordDto.getEndTime())){
            wrapper.le("create_time", LocalDateTimeUtils.localTime(searchConsumeRecordDto.getEndTime()));
        }
        IPage<ConsumeRecord> page = baseMapper.selectPage(new Page<>(searchConsumeRecordDto.getCurrentPage(), searchConsumeRecordDto.getPageSize()), wrapper);
        result.setPages(page.getPages());
        result.setCurrent(page.getCurrent());
        result.setSize(page.getSize());
        result.setTotal(page.getTotal());
        List<ConsumeRecord> list = page.getRecords();
        if(!list.isEmpty()){
            List<ConsumeRecordDto> dtoList = new ArrayList<>();
            list.forEach(e->{
                ConsumeRecordDto dto=new ConsumeRecordDto();
                BeanUtils.copyProperties(e, dto);
                dto.setCreateTime(LocalDateTimeUtils.localTimeStr(e.getCreateTime()));
                dtoList.add(dto);
            });
            result.setRecords(dtoList);
        }
        return result;
    }
}
