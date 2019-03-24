package com.eco.wisdompark.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.eco.wisdompark.domain.dto.req.bus.RideBusDto;
import com.eco.wisdompark.domain.dto.req.bus.SearchBusDto;
import com.eco.wisdompark.domain.dto.req.pos.SearchPosDto;
import com.eco.wisdompark.domain.model.Bus;
import com.eco.wisdompark.domain.model.Pos;

import java.util.List;

 /**
  * 班车服务类
  *
  * @author zhangkai
  * @date 2019/3/24 上午11:46
  */
public interface BusService extends IService<Bus> {

    /**
     * 根据条件查询班车信息
     * @param searchBusDto
     * @return
     */
    List<Bus> getBusByQuery(SearchBusDto searchBusDto);

     /**
      * 刷卡乘车
      * @param rideBusDto
      */
    void cardRideBus(RideBusDto rideBusDto);
}
