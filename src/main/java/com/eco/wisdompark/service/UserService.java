package com.eco.wisdompark.service;

import com.eco.wisdompark.domain.model.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 人员表 服务类
 * </p>
 *
 * @author litao
 * @since 2018-12-28
 */
public interface UserService extends IService<User> {


    /**
     * 查询组织架构Id的所有人员数量
     * */
    public Integer countByDept(Integer deptId);

}
