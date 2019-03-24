package com.eco.wisdompark.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eco.wisdompark.domain.dto.req.bus.BusRecordDto;
import com.eco.wisdompark.domain.dto.req.bus.SearchBusRecordDto;
import com.eco.wisdompark.domain.model.Athletes;
import com.eco.wisdompark.domain.model.AthletesTeam;
import com.eco.wisdompark.domain.model.BusRecord;
import com.eco.wisdompark.domain.model.SysUser;
import com.eco.wisdompark.enums.Gender;
import com.eco.wisdompark.mapper.BusRecordMapper;
import com.eco.wisdompark.service.AthletesService;
import com.eco.wisdompark.service.AthletesTeamService;
import com.eco.wisdompark.service.BusRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * <p>
 * 班车乘车记录 服务实现类
 * </p>
 *
 * @author zhangkai
 * @since 2019-03-24
 */
@Service
public class BusRecordServiceImpl extends ServiceImpl<BusRecordMapper, BusRecord> implements BusRecordService {

    @Autowired
    private AthletesService athletesService;

    @Autowired
    private AthletesTeamService athletesTeamService;

    @Override
    public IPage<BusRecordDto> getBusRecordByQuery(SearchBusRecordDto searchBusRecordDto) {

        IPage<BusRecordDto> busRecordDtoPage = new Page<>();
        QueryWrapper<BusRecord> wrapper = new QueryWrapper<>();

        if (searchBusRecordDto.getBusId() != null) {
            wrapper.like("bus_id", searchBusRecordDto.getBusId());
        }
        if (StringUtils.isNotBlank(searchBusRecordDto.getCardId())) {
            wrapper.like("card_id", searchBusRecordDto.getCardId());
        }
        IPage<BusRecord> busRecordPage = baseMapper.selectPage(new Page<>(searchBusRecordDto.getCurrentPage(), searchBusRecordDto.getPageSize()), wrapper);
        if(busRecordPage == null){
            busRecordDtoPage = new Page<>(searchBusRecordDto.getCurrentPage(),searchBusRecordDto.getPageSize());
            busRecordDtoPage.setRecords(Lists.newArrayList());
            return busRecordDtoPage;
        }
        BeanUtils.copyProperties(busRecordPage,busRecordDtoPage);
        if(!CollectionUtils.isEmpty(busRecordPage.getRecords())){
            List<BusRecordDto> busRecordDtoList = Lists.newArrayList();
            busRecordPage.getRecords().forEach(e -> {
                BusRecordDto busRecordDto = new BusRecordDto();
                BeanUtils.copyProperties(e,busRecordDto);
                busRecordDto.setCardSerialNo(e.getCardSerialno());
                // 查询运动员信息
                Athletes athletes = athletesService.getById(e.getUserId());
                if(athletes != null){
                    BeanUtils.copyProperties(athletes,busRecordDto);
                    busRecordDto.setGender(Gender.valueOf(athletes.getGender()).getDescription());
                    if(athletes.getTeamId() != null){
                        AthletesTeam athletesTeam = athletesTeamService.getById(athletes.getTeamId());
                        if(athletesTeam != null){
                            busRecordDto.setTeam(athletesTeam.getTeamName());
                        }
                    }
                }
                busRecordDtoList.add(busRecordDto);
            });
            busRecordDtoPage.setRecords(busRecordDtoList);
        }
        return busRecordDtoPage;
    }
}
