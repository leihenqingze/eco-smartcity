package com.eco.wisdompark.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.eco.wisdompark.common.dto.ResponseData;
import com.eco.wisdompark.common.utils.HttpClient;
import com.eco.wisdompark.common.utils.JsLifeUtils;
import com.eco.wisdompark.common.utils.RedisUtil;
import com.eco.wisdompark.domain.dto.req.JsLife.*;
import com.eco.wisdompark.domain.dto.req.user.GetUserDto;
import com.eco.wisdompark.domain.dto.resp.EntranceGuardInfoListDto;
import com.eco.wisdompark.domain.model.ReceivePersoninfo;
import com.eco.wisdompark.domain.model.User;
import com.eco.wisdompark.service.ReceivePersoninfoService;
import com.eco.wisdompark.service.UserService;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
  * 门禁controller
  *
  * @author zhangkai
  * @date 2019/5/18 下午8:44
  */
@RestController
@RequestMapping("api/entrance-guard")
@Api(value = "门禁相关Api", description = "门禁相关Api")
@Slf4j
public class EntranceGuardController {

     private static final String jslife_business_url = "http://www.jslife.com.cn/jsaims/as";

     private static final String customer_id = "000000008002303";

     private static final String search_service_id = "3c.door.querydoors";

    private static final String open_service_id = "3c.door.opendoor";

     private static final String sign_key = "f90f4d41d3e95a4ce2eacea8cfb058e5";

     private static final String area_code = "p180905103";

     private static final int version_code = 2;

     @Autowired
     private RedisUtil redisUtil;

     @Autowired
     private JsLifeUtils jsLifeUtils;

     @Autowired
     private UserService userService;

     @Autowired
     private ReceivePersoninfoService receivePersoninfoService;

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ApiOperation(value = "获取所有可用门禁", httpMethod = "POST")
    public ResponseData<List<EntranceGuardInfoListDto>> getEntranceGuardList(@RequestBody GetUserDto getUserDto){

        String personCode = getPersonCode(getUserDto.getId());

        if(StringUtils.isBlank(personCode)){
            log.error(">>>>>>>>>>request search_entrance_guard personCode is empty");
            return ResponseData.ERROR("数据查询失败!");
        }

        // 封装请求参数
        CommonRequestParam commonRequestParam = packageSearchRequestParam(personCode);

        // 获取token
        String token = getRequestToken();
        if(StringUtils.isBlank(token)){
            log.error(">>>>>>>>>>request search_entrance_guard token is empty");
            return ResponseData.ERROR("数据查询失败!");
        }

        Map<String,Object> paramMap;
        try{
            paramMap = packageRequestParamMap(token,JSON.toJSONString(commonRequestParam));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseData.ERROR("数据查询失败!");
        }

        // 请求获取门禁接口
        String result = HttpClient.doPost(jslife_business_url,paramMap);

        if(StringUtils.isBlank(result)){
            log.error(">>>>>>>>>>request search_entrance_guard response_result is empty");
            return ResponseData.ERROR("数据查询失败!");
        }

        SearchEntranceGuardResponseMsg responseMsg = JSONObject.parseObject(result,SearchEntranceGuardResponseMsg.class);

        log.info(">>>>>>>request search_entrance_guard result:{}",responseMsg.toString());

        if(responseMsg.getResultCode() != 0){
            log.error(">>>>>>>>>>request search_entrance_guard response_result_code is {}",responseMsg.getResultCode());
            return ResponseData.ERROR("数据查询失败!");
        }

        // 封装返回对象信息
        // List<EntranceGuardInfoListDto> entranceGuardInfoListDtos = packageEntranceGuardInfoList(responseMsg);

        // **************************先写死返回一条数据****************************
        EntranceGuardInfoListDto entranceGuardInfoListDto = new EntranceGuardInfoListDto();
        entranceGuardInfoListDto.setDoorId("9eab05d9aab04377ba1243f74fe9b776");
        entranceGuardInfoListDto.setDoorName("门锁_4_103");

        SearchEntranceGuardCardInfo cardInfo = new SearchEntranceGuardCardInfo();
        cardInfo.setCardId("bd340a2f5974472dbaec171a08b653e5");
        cardInfo.setPhysicalNo("3D9410E5");

        entranceGuardInfoListDto.setCardList(Lists.newArrayList(cardInfo));

        List<EntranceGuardInfoListDto> entranceGuardInfoListDtos = Lists.newArrayList(entranceGuardInfoListDto);
        // **************************先写死返回一条数据****************************

        return ResponseData.OK(entranceGuardInfoListDtos);
    }

    @RequestMapping(value = "/open", method = RequestMethod.POST)
    @ApiOperation(value = "开门", httpMethod = "POST")
    public ResponseData openDoor(@RequestBody OpenDoorDto openDoorDto){

        if(openDoorDto == null){
            log.error(">>>>>>>request open_door openDoorDto is null");
            return ResponseData.ERROR("开门失败!");
        }

        if(StringUtils.isBlank(openDoorDto.getCardId()) || StringUtils.isBlank(openDoorDto.getDoorId())){
            log.error(">>>>>>>request open_door cardId or doorId is empty, cardId:{},cardId:{}",openDoorDto.getCardId(),openDoorDto.getDoorId());
            return ResponseData.ERROR("开门失败!");
        }
        // 封装请求参数
        CommonRequestParam commonRequestParam = packageOpenRequestParam(openDoorDto);

        // 获取token
        String token = getRequestToken();
        if(StringUtils.isBlank(token)){
            log.error(">>>>>>>>>>request open_door token is empty");
            return ResponseData.ERROR("开门失败!");
        }

        Map<String,Object> paramMap;
        try{
            paramMap = packageRequestParamMap(token,JSON.toJSONString(commonRequestParam));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseData.ERROR("开门失败!");
        }

        // 请求开门接口
        String result = HttpClient.doPost(jslife_business_url,paramMap);

        if(StringUtils.isBlank(result)){
            log.error(">>>>>>>>>>request open_door response_result is empty");
            return ResponseData.ERROR("开门失败!");
        }

        SearchEntranceGuardResponseMsg responseMsg = JSONObject.parseObject(result,SearchEntranceGuardResponseMsg.class);

        log.info(">>>>>>>request open_door result:{}",responseMsg.toString());

        if(responseMsg.getResultCode() != 0){
            log.error(">>>>>>>>>>request open_door response_result_code is {}",responseMsg.getResultCode());
            return ResponseData.ERROR("开门失败!");
        }

        return ResponseData.OK();
    }

    private CommonRequestParam packageSearchRequestParam(String personCode){
        CommonRequestParam commonRequestParam = new CommonRequestParam();
        commonRequestParam.setServiceId(search_service_id);
        commonRequestParam.setRequestType("DATA");

        SearchEntranceGuardBusinessParam searchBusinessParam = new SearchEntranceGuardBusinessParam();
        searchBusinessParam.setAreaCode(area_code);
        searchBusinessParam.setPersonCode(personCode);
        commonRequestParam.setAttributes(searchBusinessParam);

        return commonRequestParam;
    }

    private String getPersonCode(Integer userId){
        String personCode = "";
        if(userId == null){
            return personCode;
        }
        User user = userService.getById(userId);
        log.info(">>>>>>>>>>>getPersonCode:user is {}",JSON.toJSONString(user));
        if(user == null || StringUtils.isBlank(user.getItemId())){
            return personCode;
        }
        ReceivePersoninfo receivePersoninfo = receivePersoninfoService.getReceivePersonInfoByItemId(user.getItemId());
        log.info(">>>>>>>>>>>getPersonCode:receivePersoninfo is {}",JSON.toJSONString(receivePersoninfo));
        if(receivePersoninfo == null || StringUtils.isBlank(receivePersoninfo.getCode())){
            return personCode;
        }
        return receivePersoninfo.getCode();
    }

    private CommonRequestParam packageOpenRequestParam(OpenDoorDto openDoorDto){
        CommonRequestParam commonRequestParam = new CommonRequestParam();
        commonRequestParam.setServiceId(open_service_id);
        commonRequestParam.setRequestType("ACTION");

        OpenDoorBusinessDto openDoorBusinessDto = new OpenDoorBusinessDto();
        BeanUtils.copyProperties(openDoorDto,openDoorBusinessDto);

        commonRequestParam.setAttributes(openDoorBusinessDto);
        return commonRequestParam;
    }

    private String getRequestToken(){
        String token;
        if(redisUtil.get("js_token") == null){
            // 调用登录接口获取token
            token = jsLifeUtils.getLoginToken();
        }else {
            token = redisUtil.get("js_token").toString();
        }
        return token;
    }

    private static String toHexString(byte[] bytes) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            buffer.append(String.format("%02X", bytes[i]));
        }
        return buffer.toString();
    }

    private Map<String,Object> packageRequestParamMap(String token,String paramStr) throws Exception {

        MessageDigest md5Tool = MessageDigest.getInstance("MD5");
        byte[] md5Data = md5Tool.digest((paramStr+sign_key).getBytes("UTF-8"));

        String sn = toHexString(md5Data);
        Map<String,Object> clientMap = new HashMap<>();
        clientMap.put("cid",customer_id);
        clientMap.put("tn",token);
        clientMap.put("sn",sn);
        clientMap.put("v",version_code);
        clientMap.put("p",paramStr);

        return clientMap;
    }

    private List<EntranceGuardInfoListDto> packageEntranceGuardInfoList(SearchEntranceGuardResponseMsg responseMsg){
        List<EntranceGuardInfoListDto> entranceGuardInfoListDtos = Lists.newArrayList();

        if(CollectionUtils.isEmpty(responseMsg.getDataItems())){
            return entranceGuardInfoListDtos;
        }

        responseMsg.getDataItems().forEach(entranceGuardDataItem -> {
            EntranceGuardInfoListDto entranceGuardInfoListDto = new EntranceGuardInfoListDto();
            SearchEntranceGuardDoorInfo doorInfo = entranceGuardDataItem.getAttributes();
            List<SearchEntranceGuardSubItem> cardInfoList = entranceGuardDataItem.getSubItems();
            if(doorInfo != null && !CollectionUtils.isEmpty(cardInfoList)){
                entranceGuardInfoListDto.setDoorId(doorInfo.getDoorId());
                entranceGuardInfoListDto.setDoorName(doorInfo.getDoorName());
                List<SearchEntranceGuardCardInfo> cardList = Lists.newArrayList();
                cardInfoList.forEach(cardInfo-> {
                    cardList.add(cardInfo.getAttributes());
                });
                entranceGuardInfoListDto.setCardList(cardList);
            }
            entranceGuardInfoListDtos.add(entranceGuardInfoListDto);
        });

        return entranceGuardInfoListDtos;
    }

}
