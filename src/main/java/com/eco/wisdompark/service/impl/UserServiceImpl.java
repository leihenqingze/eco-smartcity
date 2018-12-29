package com.eco.wisdompark.service.impl;

import com.eco.wisdompark.domain.model.User;
import com.eco.wisdompark.mapper.UserMapper;
import com.eco.wisdompark.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 人员表 服务实现类
 * </p>
 *
 * @author litao
 * @since 2018-12-28
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
