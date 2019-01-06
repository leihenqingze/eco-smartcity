package com.eco.wisdompark.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.eco.wisdompark.common.dto.ResponseData;
import com.eco.wisdompark.common.exceptions.WisdomParkException;
import com.eco.wisdompark.domain.dto.req.dept.*;
import com.eco.wisdompark.domain.model.Dept;
import com.eco.wisdompark.enums.ConsumeIdentity;
import com.eco.wisdompark.mapper.DeptMapper;
import com.eco.wisdompark.service.DeptService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eco.wisdompark.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 组织架构 服务实现类
 * </p>
 *
 * @author litao
 * @since 2018-12-28
 */
@Service
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements DeptService {

    @Autowired
    private UserService userService;


    @Override
    public Integer addDeptLevel1(AddLevel1DeptDto addLevel1DeptDto) {

        if (this.isExists(addLevel1DeptDto.getDeptName()) > 0)
            throw new WisdomParkException(ResponseData.STATUS_CODE_463, "组织架构名称已经存在");
        Dept dept = new Dept();
        dept.setDeptUpId(0);
        dept.setDeptName(addLevel1DeptDto.getDeptName());
        dept.setDeptUpDownStr("0");
        ConsumeIdentity consumeIdentity = ConsumeIdentity.valueOf(addLevel1DeptDto.getConsumeIdentity());
        if (consumeIdentity == null) throw new WisdomParkException(ResponseData.STATUS_CODE_464, "未匹配到消费类型");
        dept.setConsumeIdentity(consumeIdentity.getCode());
        Integer result = baseMapper.insert(dept);
        return result;
    }

    @Override
    public Integer addDeptLevel2(AddLevel2DeptDto addLevel2DeptDto) {
        if (StringUtils.isBlank(addLevel2DeptDto.getDeptName()))
            throw new WisdomParkException(ResponseData.STATUS_CODE_465, "请输入组织架构名称");
        if (this.isExists(addLevel2DeptDto.getDeptName()) > 0)
            throw new WisdomParkException(ResponseData.STATUS_CODE_463, "组织架构名称已经存在");
        Dept l1Dept = baseMapper.selectById(addLevel2DeptDto.getId());
        Integer result = null;
        if (l1Dept != null) {
            Dept dept = new Dept();
            dept.setDeptUpId(l1Dept.getId());
            dept.setDeptName(addLevel2DeptDto.getDeptName());
            dept.setConsumeIdentity(l1Dept.getConsumeIdentity());
            dept.setDeptUpDownStr(l1Dept.getDeptUpDownStr() + "|" + l1Dept.getId());
            result = baseMapper.insert(dept);
        }
        return result;
    }

    @Override
    public List<DeptDto> getLevel1Dept(GetLevel1DeptDto getLevel1DeptDto) {
        QueryWrapper<Dept> wrapper = new QueryWrapper<Dept>();
        wrapper.eq("dept_up_id", 0);
        if (StringUtils.isNotBlank(getLevel1DeptDto.getDeptName())) {
            wrapper.like("dept_name", getLevel1DeptDto.getDeptName());
        }
        if (getLevel1DeptDto.getConsumeIdentity() != null) {
            wrapper.eq("consume_identity", getLevel1DeptDto.getConsumeIdentity());
        }
        List<Dept> depts = baseMapper.selectList(wrapper);
        return this.convertDto(depts);
    }

    @Override
    public List<DeptDto> getLevel2Dept(AddLevel2DeptDto addLevel2DeptDto) {

        QueryWrapper<Dept> wrapper = new QueryWrapper<Dept>();
        wrapper.eq("dept_up_id", addLevel2DeptDto.getId());
        if (StringUtils.isNotBlank(addLevel2DeptDto.getDeptName())) {
            wrapper.like("dept_name", addLevel2DeptDto.getDeptName());
        }
        if (addLevel2DeptDto.getConsumeIdentity() != null) {
            wrapper.eq("consume_identity", addLevel2DeptDto.getConsumeIdentity());
        }
        List<Dept> depts = baseMapper.selectList(wrapper);
        return this.convertDto(depts);
    }

    @Override
    public Integer delDept(DelDeptDto delDeptDto) {

        if (userService.countByDept(delDeptDto.getId()) > 0)
            throw new WisdomParkException(ResponseData.STATUS_CODE_467, "组织架构下存在人员无法删除");
        Dept dept = baseMapper.selectById(delDeptDto.getId());
        if (dept.equals(0)) {
            QueryWrapper<Dept> wrapper = new QueryWrapper<Dept>();
            wrapper.eq("dept_up_id", delDeptDto.getId());
            Integer countLevel2 = baseMapper.selectCount(wrapper);
            if (countLevel2 > 0) {
                throw new WisdomParkException(ResponseData.STATUS_CODE_468, "存在二级组织架构无法删除");
            }
        }
        return baseMapper.deleteById(delDeptDto.getId());
    }

    @Override
    public List<DeptAllDto> getDeptAll() {
        List<DeptAllDto> result = new ArrayList<>();
        QueryWrapper<Dept> wrapper = new QueryWrapper<Dept>();
        wrapper.eq("dept_up_id", 0);
        List<Dept> level1 = baseMapper.selectList(wrapper);
        if (!level1.isEmpty()) {
            level1.forEach(l1 -> {
                DeptAllDto dto = new DeptAllDto();
                dto.setValue(l1.getId());
                dto.setLabel(l1.getDeptName());
                if (l1.getId() != null && l1.getId() > 0) {
                    QueryWrapper<Dept> l2wrapper = new QueryWrapper<Dept>();
                    l2wrapper.eq("dept_up_id", l1.getId());
                    List<Dept> level2 = baseMapper.selectList(l2wrapper);
                    if (!level2.isEmpty()) {
                        List<DeptAllDto> children = new ArrayList<>();
                        level2.forEach(l2 -> {
                            DeptAllDto l2Dto = new DeptAllDto();
                            l2Dto.setValue(l2.getId());
                            l2Dto.setLabel(l2.getDeptName());
                            children.add(l2Dto);
                        });
                        dto.setChildren(children);

                    }
                }
                result.add(dto);

            });
        }
        return result;
    }

    @Override
    public String getDeptName(Integer id) {
        StringBuffer stringBuffer = new StringBuffer();
        if (id != null && id > 0) {
            Dept deptl2 = baseMapper.selectById(id);
            if (deptl2 != null && deptl2.getDeptUpId() > 0) {
                Dept deptl1 = baseMapper.selectById(deptl2.getDeptUpId());
                stringBuffer.append(deptl1.getDeptName() + "/" + deptl2.getDeptName());
            }
        }
        return stringBuffer.toString();
    }

    @Override
    public List<DeptDto> getLevel2Dept(GetLevel2DeptDto getLevel2DeptDto) {
        QueryWrapper<Dept> wrapper = new QueryWrapper<Dept>();
        if (StringUtils.isNotBlank(getLevel2DeptDto.getDeptName())) {
            wrapper.like("dept_name", getLevel2DeptDto.getDeptName());
        }
        if (getLevel2DeptDto.getConsumeIdentity() != null) {
            wrapper.eq("consume_identity", getLevel2DeptDto.getConsumeIdentity());
        }
        List<Dept> depts = baseMapper.selectList(wrapper);
        return this.convertDto(depts);
    }

    @Override
    public List<DeptAllDto> getDeptAllByConsumeIdentity(GetLevel1DeptByIdentityDto getLevel1DeptByIdentityDto) {
        List<DeptAllDto> result=new ArrayList<>();
        QueryWrapper<Dept> wrapper = new QueryWrapper<Dept>();
        wrapper.eq("dept_up_id", 0);
        if(getLevel1DeptByIdentityDto.getConsumeIdentity() != null){
            wrapper.eq("consume_identity",getLevel1DeptByIdentityDto.getConsumeIdentity());
        }
        List<Dept> level1=baseMapper.selectList(wrapper);
        if(!level1.isEmpty() ){
            level1.forEach(l1->{
                DeptAllDto dto=new DeptAllDto();
                dto.setValue(l1.getId());
                dto.setLabel(l1.getDeptName());
                if(l1.getId()!=null && l1.getId()>0 ){
                    QueryWrapper<Dept> l2wrapper = new QueryWrapper<Dept>();
                    l2wrapper.eq("dept_up_id", l1.getId());
                    List<Dept> level2=baseMapper.selectList(l2wrapper);
                    if(!level2.isEmpty()){
                        List<DeptAllDto> children=new ArrayList<>();
                        level2.forEach(l2->{
                            DeptAllDto l2Dto=new DeptAllDto();
                            l2Dto.setValue(l2.getId());
                            l2Dto.setLabel(l2.getDeptName());
                            children.add(l2Dto);
                        });
                        dto.setChildren(children);

                    }
                }
                result.add(dto);

            });
        }
        return result;
    }

    private Integer isExists(String deptName) {
        QueryWrapper<Dept> wrapper = new QueryWrapper<Dept>();
        wrapper.eq("dept_name", deptName);
        return baseMapper.selectCount(wrapper);

    }

    private List<DeptDto> convertDto(List<Dept> depts) {
        List<DeptDto> result = new ArrayList<DeptDto>();
        if (depts != null && depts.size() > 0) {
            depts.forEach(e -> {
                DeptDto dto = new DeptDto();
                BeanUtils.copyProperties(e, dto);
                result.add(dto);
            });
        }
        return result;
    }
}
