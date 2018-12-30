package com.eco.wisdompark.service;

import com.eco.wisdompark.domain.dto.req.dept.*;
import com.eco.wisdompark.domain.model.Dept;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 组织架构 服务类
 * </p>
 *
 * @author litao
 * @since 2018-12-28
 */
public interface DeptService extends IService<Dept> {

    public Integer addDeptLevel1(AddLevel1DeptDto addLevel1DeptDto);

    public Integer addDeptLevel2(AddLevel2DeptDto addLevel2DeptDto);

    public List<DeptDto> getLevel1Dept(GetLevel1DeptDto getLevel1DeptDto);

    public List<DeptDto> getLevel2Dept(AddLevel2DeptDto addLevel2DeptDto);

    public Integer delDept(DelDeptDto delDeptDto);





}
