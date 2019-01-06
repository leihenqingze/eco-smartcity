package com.eco.wisdompark.service;

/**
 * <p>
 * 退卡 服务类
 * </p>
 *
 * @author litao
 * @since 2018-12-28
 */
public interface ReturnCardService {

    /**
     * 退卡操作
     *
     * @param cpuCardId CPU卡ID
     * @return 是否成功
     */
    boolean returnCard(String cpuCardId);

}
