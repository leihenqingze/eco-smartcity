package com.eco.smartcity.service.impl;

import com.eco.smartcity.domain.Test;
import com.eco.smartcity.mapper.TestMapper;
import com.eco.smartcity.service.TestService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author litao
 * @since 2018-10-16
 */
@Service
public class TestServiceImpl extends ServiceImpl<TestMapper, Test> implements TestService {

}
