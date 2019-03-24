package com.eco.wisdompark.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eco.wisdompark.common.dto.ResponseData;
import com.eco.wisdompark.common.exceptions.WisdomParkException;
import com.eco.wisdompark.common.utils.LocalDateTimeUtils;
import com.eco.wisdompark.domain.dto.req.bus.RideBusDto;
import com.eco.wisdompark.domain.dto.req.bus.SearchBusDto;
import com.eco.wisdompark.domain.dto.req.pos.SearchPosDto;
import com.eco.wisdompark.domain.model.Athletes;
import com.eco.wisdompark.domain.model.Bus;
import com.eco.wisdompark.domain.model.BusRecord;
import com.eco.wisdompark.domain.model.Pos;
import com.eco.wisdompark.mapper.BusMapper;
import com.eco.wisdompark.mapper.PosMapper;
import com.eco.wisdompark.service.AthletesService;
import com.eco.wisdompark.service.BusRecordService;
import com.eco.wisdompark.service.BusService;
import com.eco.wisdompark.service.PosService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

 /**
  * <p>
  * 班车 服务实现类
  * </p>
  *
  * @author zhangkai
  * @date 2019/3/24 下午12:01
  */
@Service
public class BusServiceImpl extends ServiceImpl<BusMapper, Bus> implements BusService {

    @Autowired
    private AthletesService athletesService;

    @Autowired
    private BusRecordService busRecordService;

    @Override
    public List<Bus> getBusByQuery(SearchBusDto searchBusDto) {

        QueryWrapper<Bus> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(searchBusDto.getBusNum())) {
            wrapper.eq("bus_num", searchBusDto.getBusNum());
        }
        return baseMapper.selectList(wrapper);
    }

     @Override
     public void cardRideBus(RideBusDto rideBusDto) {
         if(rideBusDto.getBusId() == null){
             throw new WisdomParkException(ResponseData.STATUS_CODE_400, "班车id不能为空");
         }
         Bus bus = getById(rideBusDto.getBusId());
         if(bus == null){
             throw new WisdomParkException(ResponseData.STATUS_CODE_400, "班车信息不存在");
         }
         if(StringUtils.isEmpty(rideBusDto.getCardId())){
             throw new WisdomParkException(ResponseData.STATUS_CODE_400, "卡id不能为空");
         }
         Athletes athletes = athletesService.getByCardId(rideBusDto.getCardId());
         if(athletes == null){
             throw new WisdomParkException(ResponseData.STATUS_CODE_400, "运动员信息不存在");
         }
         BusRecord busRecord = new BusRecord();
         BeanUtils.copyProperties(rideBusDto,busRecord);
         busRecord.setBusNum(bus.getBusNum());
         busRecord.setCardSerialno(athletes.getCardSerialno());
         busRecord.setUserId(athletes.getId());
         busRecord.setCreateTime(LocalDateTime.now());
         busRecordService.save(busRecord);
     }
 }
