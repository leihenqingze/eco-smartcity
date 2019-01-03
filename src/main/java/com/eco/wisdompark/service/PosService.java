package com.eco.wisdompark.service;

import com.eco.wisdompark.domain.dto.req.pos.SearchPosDto;
import com.eco.wisdompark.domain.dto.req.user.SearchUserDto;
import com.eco.wisdompark.domain.model.Pos;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * pos机 服务类
 * </p>
 *
 * @author litao
 * @since 2018-12-28
 */
public interface PosService extends IService<Pos> {

    /**
     * 根据条件查询POS机信息
     * @param searchPosDto
     * @return
     */
    List<Pos> getPosByQuery(SearchPosDto searchPosDto);
}
