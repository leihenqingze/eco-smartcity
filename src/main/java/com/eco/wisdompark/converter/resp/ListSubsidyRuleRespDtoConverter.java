package com.eco.wisdompark.converter.resp;

import com.eco.wisdompark.domain.dto.resp.ListSubsidyRuleRespDto;
import com.eco.wisdompark.domain.model.Dept;
import com.eco.wisdompark.domain.model.SubsidyRule;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 补助规则转换类
 */
@Component
public class ListSubsidyRuleRespDtoConverter {

    /**
     * 补助规则转换
     *
     * @param subsidyRules 补助规则
     * @param parent       上级部门
     * @param depts        补助规则对应的部门
     * @return 补助规则
     */
    public static List<ListSubsidyRuleRespDto> converter(List<SubsidyRule> subsidyRules,
                                                         Dept parent, List<Dept> depts) {
        Map<Integer, Dept> deptMap = depts.stream()
                .collect(Collectors.toMap(Dept::getId, a -> a, (k1, k2) -> k1));
        List<ListSubsidyRuleRespDto> listSubsidyRuleDtos =
                subsidyRules.stream().map(subsidyRule -> {
                    ListSubsidyRuleRespDto subsidyRuleDto = new ListSubsidyRuleRespDto();
                    BeanUtils.copyProperties(subsidyRule, subsidyRuleDto);
                    String deptName = parent.getDeptName() + "/" + deptMap.get(subsidyRule.getDeptId()).getDeptName();
                    subsidyRuleDto.setDeptName(deptName);
                    return subsidyRuleDto;
                }).collect(Collectors.toList());
        return listSubsidyRuleDtos;
    }

}
