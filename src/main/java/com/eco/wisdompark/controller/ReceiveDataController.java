package com.eco.wisdompark.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.eco.wisdompark.common.dto.ResponseData;
import com.eco.wisdompark.domain.dto.req.ReceiveDto;
import com.eco.wisdompark.domain.model.ReceiveCardinfo;
import com.eco.wisdompark.domain.model.ReceivePersoninfo;
import com.eco.wisdompark.domain.model.User;
import com.eco.wisdompark.mapper.ReceiveCardinfoMapper;
import com.eco.wisdompark.mapper.ReceivePersoninfoMapper;
import com.eco.wisdompark.mapper.UserMapper;
import com.eco.wisdompark.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Api(value = "接收数据", description = "接收捷顺推送的人员信息与卡片信息数据")
@RestController
@RequestMapping("/api/receive")
@Slf4j
public class ReceiveDataController {

    @Autowired
    private ReceivePersoninfoMapper receivePersoninfoMapper;

    @Autowired
    private ReceiveCardinfoMapper receiveCardinfoMapper;

    @Autowired
    private UserMapper userMapper;


    @Autowired
    private UserService userService;


    @RequestMapping(value = "/person/personInfo", method = RequestMethod.POST)
    @ApiOperation(value = "接收人员信息", httpMethod = "POST")
    public ResponseData<String> personInfo(@RequestBody ReceiveDto receiveDto) {
        log.info("-------> personInfo {}" + receiveDto);
        if (receiveDto != null && StringUtils.isNotBlank(receiveDto.getDataItems())) {
            JSONArray json = JSONArray.parseArray(receiveDto.getDataItems());
            if (json != null && json.size() > 0) {
                for (int i = 0; i < json.size(); i++) {
                    JSONObject job = json.getJSONObject(i);
                    if (job.containsKey("itemId") && StringUtils.isNotBlank(job.get("itemId").toString())) {
                        Integer saveOrUpdate = 0;
                        ReceivePersoninfo receivePersoninfo = receivePersoninfoMapper.selectOne(new QueryWrapper<ReceivePersoninfo>().eq("itemId", job.get("itemId").toString()));
                        if (receivePersoninfo == null) {
                            receivePersoninfo = new ReceivePersoninfo();
                            saveOrUpdate = 1;
                        }
                        receivePersoninfo.setItemId(job.get("itemId").toString());
                        if (job.containsKey("code") && StringUtils.isNotBlank(job.get("code").toString())) {
                            receivePersoninfo.setCode(job.get("code").toString());
                        }
                        if (job.containsKey("name") && StringUtils.isNotBlank(job.get("name").toString())) {
                            receivePersoninfo.setName(job.get("name").toString());
                        }
                        if (job.containsKey("parkName") && StringUtils.isNotBlank(job.get("parkName").toString())) {
                            receivePersoninfo.setParkName(job.get("parkName").toString());
                        }
                        if (job.containsKey("parkCode") && StringUtils.isNotBlank(job.get("parkCode").toString())) {
                            receivePersoninfo.setParkCode(job.get("parkCode").toString());
                        }
                        if (job.containsKey("type") && StringUtils.isNotBlank(job.get("type").toString())) {
                            receivePersoninfo.setType(job.get("type").toString());
                        }
                        if (job.containsKey("isOperator") && StringUtils.isNotBlank(job.get("isOperator").toString())) {
                            receivePersoninfo.setIsOperator(job.get("isOperator").toString());
                        }
                        if (job.containsKey("idCardType") && StringUtils.isNotBlank(job.get("idCardType").toString())) {
                            receivePersoninfo.setIdCardType(job.get("idCardType").toString());
                        }
                        if (job.containsKey("idCardNo") && StringUtils.isNotBlank(job.get("idCardNo").toString())) {
                            receivePersoninfo.setIdCardNo(job.get("idCardNo").toString());
                        }
                        if (job.containsKey("sex") && StringUtils.isNotBlank(job.get("sex").toString())) {
                            receivePersoninfo.setSex(job.get("sex").toString());
                        }
                        if (job.containsKey("age") && StringUtils.isNotBlank(job.get("age").toString())) {
                            receivePersoninfo.setAge(job.get("age").toString());
                        }
                        if (job.containsKey("nativePlace") && StringUtils.isNotBlank(job.get("nativePlace").toString())) {
                            receivePersoninfo.setNativePlace(job.get("nativePlace").toString());
                        }
                        if (job.containsKey("telephone") && StringUtils.isNotBlank(job.get("telephone").toString())) {
                            receivePersoninfo.setTelephone(job.get("telephone").toString());
                        }
                        if (job.containsKey("officeTel") && StringUtils.isNotBlank(job.get("officeTel").toString())) {
                            receivePersoninfo.setOfficeTel(job.get("officeTel").toString());
                        }
                        if (job.containsKey("address") && StringUtils.isNotBlank(job.get("address").toString())) {
                            receivePersoninfo.setAddress(job.get("address").toString());
                        }
                        if (job.containsKey("email") && StringUtils.isNotBlank(job.get("email").toString())) {
                            receivePersoninfo.setEmail(job.get("email").toString());
                        }
                        if (job.containsKey("remark") && StringUtils.isNotBlank(job.get("remark").toString())) {
                            receivePersoninfo.setRemark(job.get("remark").toString());
                        }
                        if (job.containsKey("status") && StringUtils.isNotBlank(job.get("status").toString())) {
                            receivePersoninfo.setStatus(job.get("status").toString());
                        }
                        if (job.containsKey("attach") && StringUtils.isNotBlank(job.get("attach").toString())) {
                            receivePersoninfo.setAttach(job.get("attach").toString());
                        }
                        receivePersoninfo.setCreateTime(LocalDateTime.now());
                        receivePersoninfo.setTs(LocalDateTime.now());
                        if (saveOrUpdate > 0) {
                            log.info("执行插入{}"+receivePersoninfo);
                            receivePersoninfoMapper.insert(receivePersoninfo);
                        } else {
                            receivePersoninfoMapper.updateById(receivePersoninfo);
                            log.info("执行更新{}"+receivePersoninfo);

                        }
                        if (receivePersoninfo != null && StringUtils.isNotBlank(receivePersoninfo.getTelephone())) {
                            User u = userService.getUserByPhone(receivePersoninfo.getTelephone());
                            if(u!=null){
                                log.info("用户存在执行更新 {}"+receivePersoninfo.getTelephone());
                                u.setItemId(receivePersoninfo.getItemId());
                                userMapper.updateById(u);
                            }
                            else{
                                User user = new User();
                                user.setUserName(receivePersoninfo.getName());
                                user.setPhoneNum(receivePersoninfo.getTelephone());
                                user.setIdentity(1);
                                user.setItemId(receivePersoninfo.getItemId());
                                user.setCreateTime(LocalDateTime.now());
                                user.setTs(LocalDateTime.now());
                                userMapper.insert(user);
                                log.info("用户不存在 创建用户 {}"+user);
                            }
                        }

                    }
                }
            }
        }
        return ResponseData.OK("人员信息推送完成");

    }

    @RequestMapping(value = "/card/cardInfo", method = RequestMethod.POST)
    @ApiOperation(value = "接收卡片信息", httpMethod = "POST")
    public ResponseData<String> cardInfo(@RequestBody ReceiveDto receiveDto) {
        log.info("-------> cardInfo {}" + receiveDto);
        if (receiveDto != null && StringUtils.isNotBlank(receiveDto.getDataItems())) {
            JSONArray json = JSONArray.parseArray(receiveDto.getDataItems());
            if (json != null && json.size() > 0) {
                for (int i = 0; i < json.size(); i++) {
                    JSONObject job = json.getJSONObject(i);
                    if (job.containsKey("itemId") && StringUtils.isNotBlank(job.get("itemId").toString())) {
                        Integer saveOrUpdate = 0;
                        ReceiveCardinfo receiveCardinfo = receiveCardinfoMapper.selectOne(new QueryWrapper<ReceiveCardinfo>().eq("itemId", job.get("itemId").toString()));
                        if (receiveCardinfo == null) {
                            receiveCardinfo = new ReceiveCardinfo();
                            saveOrUpdate = 1;
                        }
                        receiveCardinfo.setItemId(job.get("itemId").toString());
                        if (job.containsKey("idno") && StringUtils.isNotBlank(job.get("idno").toString())) {
                            receiveCardinfo.setIdno(job.get("idno").toString());
                        }
                        if (job.containsKey("personId") && StringUtils.isNotBlank(job.get("personId").toString())) {
                            receiveCardinfo.setPersonId(job.get("personId").toString());
                        }
                        if (job.containsKey("personCode") && StringUtils.isNotBlank(job.get("personCode").toString())) {
                            receiveCardinfo.setParkCode(job.get("personCode").toString());
                        }
                        if (job.containsKey("personCode") && StringUtils.isNotBlank(job.get("personCode").toString())) {
                            receiveCardinfo.setParkCode(job.get("personCode").toString());
                        }
                        if (job.containsKey("personName") && StringUtils.isNotBlank(job.get("personName").toString())) {
                            receiveCardinfo.setParkName(job.get("personName").toString());
                        }
                        if (job.containsKey("carNumber") && StringUtils.isNotBlank(job.get("carNumber").toString())) {
                            receiveCardinfo.setCarNumber(job.get("carNumber").toString());
                        }
                        if (job.containsKey("parkName") && StringUtils.isNotBlank(job.get("parkName").toString())) {
                            receiveCardinfo.setParkName(job.get("parkName").toString());
                        }
                        if (job.containsKey("parkCode") && StringUtils.isNotBlank(job.get("parkCode").toString())) {
                            receiveCardinfo.setParkCode(job.get("parkCode").toString());
                        }
                        if (job.containsKey("mediaType") && StringUtils.isNotBlank(job.get("mediaType").toString())) {
                            receiveCardinfo.setMediaType(job.get("mediaType").toString());
                        }
                        if (job.containsKey("status") && StringUtils.isNotBlank(job.get("status").toString())) {
                            receiveCardinfo.setStatus(job.get("status").toString());
                        }
                        if (job.containsKey("startTime") && StringUtils.isNotBlank(job.get("startTime").toString())) {
                            receiveCardinfo.setStartTime(job.get("startTime").toString());
                        }
                        if (job.containsKey("endTime") && StringUtils.isNotBlank(job.get("endTime").toString())) {
                            receiveCardinfo.setEndTime(job.get("endTime").toString());
                        }
                        if (job.containsKey("cardType") && StringUtils.isNotBlank(job.get("cardType").toString())) {
                            receiveCardinfo.setCardType(job.get("cardType").toString());
                        }
                        if (job.containsKey("balance") && StringUtils.isNotBlank(job.get("balance").toString())) {
                            receiveCardinfo.setBalance(job.get("balance").toString());
                        }
                        if (job.containsKey("operateTime") && StringUtils.isNotBlank(job.get("operateTime").toString())) {
                            receiveCardinfo.setOperateTime(job.get("operateTime").toString());
                        }
                        if (job.containsKey("operateName") && StringUtils.isNotBlank(job.get("operateName").toString())) {
                            receiveCardinfo.setOperateName(job.get("operateName").toString());
                        }
                        if (job.containsKey("operateMoney") && StringUtils.isNotBlank(job.get("operateMoney").toString())) {
                            receiveCardinfo.setOperateMoney(job.get("operateMoney").toString());
                        }
                        if (job.containsKey("attach") && StringUtils.isNotBlank(job.get("attach").toString())) {
                            receiveCardinfo.setAttach(job.get("attach").toString());
                        }
                        receiveCardinfo.setCreateTime(LocalDateTime.now());
                        receiveCardinfo.setTs(LocalDateTime.now());
                        if (saveOrUpdate > 0) {
                            receiveCardinfoMapper.insert(receiveCardinfo);
                        } else {
                            receiveCardinfoMapper.updateById(receiveCardinfo);
                        }
                    }
                }
            }


        }
        return ResponseData.OK("卡务信息推送完成");

    }
}
