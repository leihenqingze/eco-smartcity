package com.eco.wisdompark.controller;


import com.eco.wisdompark.common.dto.ResponseData;
import com.eco.wisdompark.domain.dto.req.dept.*;
import com.eco.wisdompark.service.DeptService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 组织架构 前端控制器
 * </p>
 *
 * @author litao
 * @since 2018-12-28
 */
@RestController
@RequestMapping("api/dept")
@Api(value = "组织架构API", description = "组织架构相关API")
public class DeptController {

    @Autowired
    private DeptService deptService;


    @RequestMapping(value = "/addDeptLevel1", method = RequestMethod.POST)
    @ApiOperation(value = "添加组织架构一级", httpMethod = "POST")
    public ResponseData<Integer> addDeptLevel1( @RequestBody AddLevel1DeptDto addLevel1DeptDto) {
        Integer result = deptService.addDeptLevel1(addLevel1DeptDto);
        return ResponseData.OK(result);
    }

    @RequestMapping(value = "/addDeptLevel2", method = RequestMethod.POST)
    @ApiOperation(value = "添加组织架构二级", httpMethod = "POST")
    public ResponseData<Integer> addDeptLevel2( @RequestBody AddLevel2DeptDto addLevel2DeptDto) {

        Integer result=deptService.addDeptLevel2(addLevel2DeptDto);
        return ResponseData.OK(result);
    }

    @RequestMapping(value = "/getLevel1Dept", method = RequestMethod.POST)
    public ResponseData<List<DeptDto>> getLevel1Dept( @RequestBody GetLevel1DeptDto getLevel1DeptDto) {
        List<DeptDto> result=deptService.getLevel1Dept(getLevel1DeptDto);
        return ResponseData.OK(result);
    }

    @RequestMapping(value = "/getDeptAll", method = RequestMethod.POST)
    @ApiOperation(value = "获取全部的组织架构信息", httpMethod = "POST")
    public ResponseData<List<DeptAllDto>> getDeptAll( ) {
        List<DeptAllDto> result=deptService.getDeptAll();
        return ResponseData.OK(result);
    }

    @RequestMapping(value = "/getDeptAllByConsumeIdentity", method = RequestMethod.POST)
    @ApiOperation(value = "根据消费类型获取全部的组织架构信息", httpMethod = "POST")
    public ResponseData<List<DeptAllDto>> getDeptAllByConsumeIdentity(@RequestBody GetLevel1DeptByIdentityDto getLevel1DeptByIdentityDto) {
        List<DeptAllDto> result=deptService.getDeptAllByConsumeIdentity(getLevel1DeptByIdentityDto);
        return ResponseData.OK(result);
    }


    @RequestMapping(value = "/getLevel2Dept", method = RequestMethod.POST)
    @ApiOperation(value = "查询组织架构二级", httpMethod = "POST")
    public ResponseData<List<DeptDto>> getLevel2Dept( @RequestBody AddLevel2DeptDto addLevel2DeptDto) {
        List<DeptDto> result=deptService.getLevel2Dept(addLevel2DeptDto);
        return ResponseData.OK(result);
    }


    @RequestMapping(value = "/delDept", method = RequestMethod.POST)
    @ApiOperation(value = "删除组织架构", httpMethod = "POST")
    public ResponseData<Integer> delDept( @RequestBody DelDeptDto delDeptDto) {
        Integer result= deptService.delDept(delDeptDto);
        return ResponseData.OK(result);
    }


}
