package com.eco.wisdompark.service;

import com.eco.wisdompark.domain.dto.req.dept.*;
import com.eco.wisdompark.domain.model.Dept;
import com.baomidou.mybatisplus.extension.service.IService;
import com.eco.wisdompark.enums.ConsumeIdentity;

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

    /**
     * 添加一级组织架构
     *
     * @param addLevel1DeptDto
     * @return
     */
    public Integer addDeptLevel1(AddLevel1DeptDto addLevel1DeptDto);

    /**
     * 添加二级组织架构
     *
     * @param addLevel2DeptDto
     * @return
     */
    public Integer addDeptLevel2(AddLevel2DeptDto addLevel2DeptDto);

    /**
     * 获取一级组织架构数据
     *
     * @param getLevel1DeptDto
     * @return
     */
    public List<DeptDto> getLevel1Dept(GetLevel1DeptDto getLevel1DeptDto);

    /**
     * 获取二级组织架构数据
     *
     * @param addLevel2DeptDto
     * @return
     */
    public List<DeptDto> getLevel2Dept(AddLevel2DeptDto addLevel2DeptDto);

    /**
     * 删除组织架构
     *
     * @param delDeptDto
     * @return
     */
    public Integer delDept(DelDeptDto delDeptDto);

}
