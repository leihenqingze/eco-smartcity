package com.eco.wisdompark.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.eco.wisdompark.domain.model.ReceivePersoninfo;
import com.eco.wisdompark.mapper.ReceivePersoninfoMapper;
import com.eco.wisdompark.service.ReceivePersoninfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author litao
 * @since 2019-07-11
 */
@Service
public class ReceivePersoninfoServiceImpl extends ServiceImpl<ReceivePersoninfoMapper, ReceivePersoninfo> implements ReceivePersoninfoService {

    @Autowired
    private ReceivePersoninfoMapper receivePersoninfoMapper;

    @Override
    public ReceivePersoninfo getReceivePersonInfoByItemId(String itemId) {
        if(StringUtils.isBlank(itemId)){
            return null;
        }
        QueryWrapper<ReceivePersoninfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("itemId", itemId);
        return receivePersoninfoMapper.selectOne(queryWrapper);
    }
}
