package com.eco.wisdompark.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eco.wisdompark.common.exceptions.WisdomParkException;
import com.eco.wisdompark.converter.req.PageReqDtoToPageConverter;
import com.eco.wisdompark.converter.resp.ListSubsidyRuleRespDtoConverter;
import com.eco.wisdompark.domain.dto.req.PageReqDto;
import com.eco.wisdompark.domain.dto.req.subsidy.ListSubsidyRuleReqDto;
import com.eco.wisdompark.domain.dto.resp.ListSubsidyRuleRespDto;
import com.eco.wisdompark.domain.model.Dept;
import com.eco.wisdompark.domain.model.SubsidyRule;
import com.eco.wisdompark.mapper.DeptMapper;
import com.eco.wisdompark.mapper.SubsidyRuleMapper;
import com.eco.wisdompark.service.SubsidyRuleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

    /**
     * 添加自动补助规则
     *
     * @param entity 补助规则
     * @return 是否成功
     */
    @Override
    public boolean save(SubsidyRule entity) {
        Dept dept = deptMapper.selectById(entity.getDeptId());
        if (Objects.isNull(dept)) {
            throw new WisdomParkException(400, "该部门不存在");
        } else if (Objects.equals(0, dept.getDeptUpId())) {
            throw new WisdomParkException(400, "该部门下不能设置自动补助规则");
        }
        return super.save(entity);
    }

    /**
     * 自动补助规则分页查询
     *
     * @param pageReqDto 分页信息
     * @return 自动补助规则列表
     */
    @Override
    public IPage<ListSubsidyRuleRespDto> findByDeptIdPage(PageReqDto<ListSubsidyRuleReqDto> pageReqDto) {
        if (Objects.isNull(pageReqDto.getQuery()) || Objects.isNull(pageReqDto.getQuery().getDeptId())) {
            return findAllByPage(pageReqDto);
        } else {
            Pair<IPage<SubsidyRule>, List<Dept>> pair = findDeptsByPage(pageReqDto);
            List<Dept> depts = pair.getRight();

            IPage<ListSubsidyRuleRespDto> listSubsidyRuleDtoIPage = new Page<>();
            BeanUtils.copyProperties(pair.getLeft(), listSubsidyRuleDtoIPage);
            List<SubsidyRule> subsidyRuleList = pair.getLeft().getRecords();

            if (CollectionUtils.isNotEmpty(subsidyRuleList)) {
                Dept dept = deptMapper.selectById(subsidyRuleList.get(0).getDeptId());
                depts.add(dept);
                Dept parent = deptMapper.selectById(dept.getDeptUpId());
                depts.add(dept);
                listSubsidyRuleDtoIPage.setRecords(ListSubsidyRuleRespDtoConverter
                        .converterList(subsidyRuleList, parent, depts));
            }
            return listSubsidyRuleDtoIPage;
        }
    }

    /**
     * 自动补助规则分页查询
     *
     * @param pageReqDto 分页信息
     * @return 自动补助规则列表
     */
    public IPage<ListSubsidyRuleRespDto> findAllByPage(PageReqDto<ListSubsidyRuleReqDto> pageReqDto) {
        IPage<SubsidyRule> subsidyRuleIPage = page(PageReqDtoToPageConverter.converter(pageReqDto),
                null);

        IPage<ListSubsidyRuleRespDto> listSubsidyRuleDtoIPage = new Page<>();
        BeanUtils.copyProperties(subsidyRuleIPage, listSubsidyRuleDtoIPage);
        List<SubsidyRule> subsidyRuleList = subsidyRuleIPage.getRecords();

        if (CollectionUtils.isNotEmpty(subsidyRuleList)) {
            List<ListSubsidyRuleRespDto> listSubsidyRuleRespDtos = subsidyRuleList.stream().map(subsidyRule -> {
                Dept dept = deptMapper.selectById(subsidyRule.getDeptId());
                Dept parent = deptMapper.selectById(dept.getDeptUpId());
                return ListSubsidyRuleRespDtoConverter.converter(subsidyRule, parent, dept);
            }).collect(Collectors.toList());
            listSubsidyRuleDtoIPage.setRecords(listSubsidyRuleRespDtos);
        }
        return listSubsidyRuleDtoIPage;
    }


    /**
     * 查询子部门和部门下的自动补助规则
     *
     * @param pageReqDto 分页请求对象
     * @return 部门下的自动补助规则, 子部门
     */
    private Pair<IPage<SubsidyRule>, List<Dept>> findDeptsByPage(PageReqDto<ListSubsidyRuleReqDto> pageReqDto) {
        QueryWrapper deptQuery = new QueryWrapper();
        deptQuery.eq("dept_up_id", pageReqDto.getQuery().getDeptId());
        List<Dept> depts = deptMapper.selectList(deptQuery);
        List<Integer> deptIds = depts.stream()
                .map(dept -> dept.getId())
                .collect(Collectors.toList());
        deptIds.add(pageReqDto.getQuery().getDeptId());

        QueryWrapper<SubsidyRule> subsidyRuleQuery = new QueryWrapper<>();
        subsidyRuleQuery.in("dept_id", deptIds);
        subsidyRuleQuery.orderByDesc("create_time");
        IPage<SubsidyRule> subsidyRuleIPage = page(PageReqDtoToPageConverter.converter(pageReqDto),
                subsidyRuleQuery);
        return Pair.of(subsidyRuleIPage, depts);
    }

}