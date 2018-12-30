package com.eco.wisdompark.controller;


import com.eco.wisdompark.common.dto.ResponseData;
import com.eco.wisdompark.domain.dto.req.dept.AddLevel1DeptDto;
import com.eco.wisdompark.domain.dto.req.dept.AddLevel2DeptDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping(value = "/addDeptLevel1", method = RequestMethod.POST)
    @ApiOperation(value = "添加组织架构一级", httpMethod = "POST")
    public ResponseData addDeptLevel1(AddLevel1DeptDto AddLevel1DeptDto) {
        return ResponseData.OK();
    }

    @RequestMapping(value = "/addDeptLevel2", method = RequestMethod.POST)
    @ApiOperation(value = "添加组织架构二级", httpMethod = "POST")
    public ResponseData addDeptLevel2(AddLevel2DeptDto AddLevel2DeptDto) {
        return ResponseData.OK();
    }

    @RequestMapping(value = "/getLevel1Dept", method = RequestMethod.POST)
    @ApiOperation(value = "查询组织架构一级", httpMethod = "POST")
    @ApiImplicitParam(name = "deptName", value = "通过名称模糊查询",  dataType = "String")
    public ResponseData getLevel1Dept(String deptName) {
        return ResponseData.OK();
    }


    @RequestMapping(value = "/getLevel2Dept", method = RequestMethod.POST)
    @ApiOperation(value = "查询组织架构二级", httpMethod = "POST")
    public ResponseData getLevel2Dept(AddLevel2DeptDto ddLevel2DeptDto) {
        return ResponseData.OK();
    }


    @RequestMapping(value = "/delDept", method = RequestMethod.POST)
    @ApiOperation(value = "删除组织架构", httpMethod = "POST")
    @ApiImplicitParam(name = "id", value = "组织架构Id",  dataType = "Integer")
    public ResponseData getLevel2Dept(Integer id) {
        return ResponseData.OK();
    }







}
