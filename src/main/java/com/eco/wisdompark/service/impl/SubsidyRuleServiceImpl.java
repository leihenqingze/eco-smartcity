package com.eco.wisdompark.service.impl;

import com.eco.wisdompark.common.exceptions.WisdomParkException;
import com.eco.wisdompark.domain.model.Dept;
import com.eco.wisdompark.domain.model.SubsidyRule;
import com.eco.wisdompark.mapper.DeptMapper;
import com.eco.wisdompark.mapper.SubsidyRuleMapper;
import com.eco.wisdompark.service.SubsidyRuleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * <p>
 * 自动补助-补助规则 服务实现类
 * </p>
 *
 * @author litao
 * @since 2018-12-28
 */
@Service
public class SubsidyRuleServiceImpl extends ServiceImpl<SubsidyRuleMapper, SubsidyRule> implements SubsidyRuleService {

    @Autowired
    private DeptMapper deptMapper;

    @Override
    public boolean save(SubsidyRule entity) {
        Dept dept = deptMapper.selectById(entity.getDeptId());
        if (Objects.isNull(dept)) {
            throw new WisdomParkException(400, "该部门不存在");
        }
        return super.save(entity);
    }
}
