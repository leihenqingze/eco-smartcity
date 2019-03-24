package com.eco.wisdompark.service;

import com.eco.wisdompark.domain.model.Athletes;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 运动员信息表 服务类
 * </p>
 *
 * @author zhangkai
 * @since 2019-03-24
 */
public interface AthletesService extends IService<Athletes> {

    /**
     * 根据卡id查找绑定的运动员信息
     * @param cardId
     * @return
     */
    Athletes getByCardId(String cardId);

}
