package com.eco.wisdompark.service;

import com.eco.wisdompark.domain.model.ReceivePersoninfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author litao
 * @since 2019-07-11
 */
public interface ReceivePersoninfoService extends IService<ReceivePersoninfo> {

    ReceivePersoninfo getReceivePersonInfoByItemId(String itemId);

}
