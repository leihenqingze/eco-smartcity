package com.eco.wisdompark.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.eco.wisdompark.domain.dto.req.pos.SearchPosDto;
import com.eco.wisdompark.domain.model.Pos;
import com.eco.wisdompark.domain.model.User;
import com.eco.wisdompark.mapper.PosMapper;
import com.eco.wisdompark.service.PosService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * pos机 服务实现类
 * </p>
 *
 * @author litao
 * @since 2018-12-28
 */
@Service
public class PosServiceImpl extends ServiceImpl<PosMapper, Pos> implements PosService {

    @Override
    public List<Pos> getPosByQuery(SearchPosDto searchPosDto) {

        QueryWrapper<Pos> wrapper = new QueryWrapper<Pos>();
        if (searchPosDto.getPosPosition() != null) {
            wrapper.eq("pos_position", searchPosDto.getPosPosition());
        }
        if (searchPosDto.getPosConsumeType() != null) {
            wrapper.like("pos_consume_type", searchPosDto.getPosConsumeType());
        }
        if (searchPosDto.getPosNum() != null) {
            wrapper.like("pos_num", searchPosDto.getPosNum());
        }
        return baseMapper.selectList(wrapper);
    }
}
