package com.eco.wisdompark.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eco.wisdompark.common.dto.ResponseData;
import com.eco.wisdompark.domain.dto.req.PageReqDto;
import com.eco.wisdompark.domain.dto.req.subsidy.AddAutoSubsidyRuleDto;
import com.eco.wisdompark.domain.dto.req.subsidy.ListSubsidyRuleReqDto;
import com.eco.wisdompark.domain.dto.resp.ListSubsidyRuleRespDto;
import com.eco.wisdompark.domain.dto.req.subsidy.RevStopSubsidyRuleDto;
import com.eco.wisdompark.domain.dto.req.subsidy.UpdateSubsidyRuleDto;
import com.eco.wisdompark.domain.model.SubsidyRule;
import com.eco.wisdompark.enums.SubsidyStatus;
import com.eco.wisdompark.service.SubsidyRuleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 自动补助-补助规则 前端控制器
 * </p>
 *
 * @author litao
 * @since 2018-12-28
 */
@RestController
@RequestMapping("/subsidy-rule")
public class SubsidyRuleController {

    @Autowired
    private SubsidyRuleService subsidyRuleService;

    @RequestMapping(value = "/autoSubsidyRule", method = RequestMethod.POST)
    @ApiOperation(value = "添加自动补助规则", httpMethod = "POST")
    public ResponseData addAutoSubsidyRule(@RequestBody AddAutoSubsidyRuleDto addAutoSubsidyRuleDto) {
        SubsidyRule subsidyRule = new SubsidyRule();
        BeanUtils.copyProperties(addAutoSubsidyRuleDto, subsidyRule);
        subsidyRule.setSubsidyStatus(SubsidyStatus.START.getCode());
        subsidyRuleService.save(subsidyRule);
        return ResponseData.OK();
    }

    @RequestMapping(value = "/revStopSubsidyRule", method = RequestMethod.PUT)
    @ApiOperation(value = "启停自动补助规则", httpMethod = "PUT")
    public ResponseData revStopSubsidyRule(@RequestBody RevStopSubsidyRuleDto revStopSubsidyRuleDto) {
        SubsidyRule subsidyRule = new SubsidyRule();
        BeanUtils.copyProperties(revStopSubsidyRuleDto, subsidyRule);
        subsidyRuleService.updateById(subsidyRule);
        return ResponseData.OK();
    }

    @RequestMapping(value = "/updateSubsidyRule", method = RequestMethod.PUT)
    @ApiOperation(value = "修改自动补助规则", httpMethod = "PUT")
    public ResponseData updateSubsidyRule(@RequestBody UpdateSubsidyRuleDto updateSubsidyRuleDto) {
        SubsidyRule subsidyRule = new SubsidyRule();
        BeanUtils.copyProperties(updateSubsidyRuleDto, subsidyRule);
        subsidyRuleService.updateById(subsidyRule);
        return ResponseData.OK();
    }

    @RequestMapping(value = "/findByDeptIdPage", method = RequestMethod.POST)
    @ApiOperation(value = "分页查询自动补助规则", httpMethod = "POST")
    public ResponseData<Page<ListSubsidyRuleRespDto>> findByDeptIdPage(
            @RequestBody PageReqDto<ListSubsidyRuleReqDto> pageReqDto) {
        IPage<ListSubsidyRuleRespDto> result = subsidyRuleService.findByDeptIdPage(pageReqDto);
        return ResponseData.OK(result);
    }

}