package com.eco.wisdompark.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.eco.wisdompark.common.dto.ResponseData;
import com.eco.wisdompark.common.exceptions.WisdomParkException;
import com.eco.wisdompark.domain.model.Athletes;
import com.eco.wisdompark.mapper.AthletesMapper;
import com.eco.wisdompark.service.AthletesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 运动员信息表 服务实现类
 * </p>
 *
 * @author zhangkai
 * @since 2019-03-24
 */
@Service
public class AthletesServiceImpl extends ServiceImpl<AthletesMapper, Athletes> implements AthletesService {
    @Override
    public Athletes getByCardId(String cardId) {
        if(StringUtils.isEmpty(cardId)){
            return null;
        }
        try{
            QueryWrapper<Athletes> athletesQueryWrapper = new QueryWrapper<>();
            athletesQueryWrapper.eq("card_id",cardId);
            Athletes athletes = baseMapper.selectOne(athletesQueryWrapper);
            return athletes;
        }catch (MyBatisSystemException e){
            e.printStackTrace();
            throw new WisdomParkException(ResponseData.STATUS_CODE_500,"卡信息重复");
        }

    }
}
