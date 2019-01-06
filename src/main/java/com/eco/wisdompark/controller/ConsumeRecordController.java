package com.eco.wisdompark.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eco.wisdompark.common.dto.ResponseData;
import com.eco.wisdompark.domain.dto.req.consumeRecord.*;
import com.eco.wisdompark.domain.dto.req.dept.AddLevel2DeptDto;
import com.eco.wisdompark.domain.dto.req.dept.DeptDto;
import com.eco.wisdompark.domain.dto.req.dept.GetLevel1DeptDto;
import com.eco.wisdompark.domain.dto.req.dept.GetLevel2DeptDto;
import com.eco.wisdompark.domain.dto.req.pos.SearchPosDto;
import com.eco.wisdompark.domain.dto.resp.ConsomeRecordRespDto;
import com.eco.wisdompark.domain.model.Pos;
import com.eco.wisdompark.domain.model.User;
import com.eco.wisdompark.enums.ConsumeIdentity;
import com.eco.wisdompark.service.ConsumeRecordService;
import com.eco.wisdompark.service.DeptService;
import com.eco.wisdompark.service.PosService;
import com.eco.wisdompark.service.UserService;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * CPU卡-消费记录表 前端控制器
 * </p>
 *
 * @author litao
 * @since 2018-12-28
 */
@RestController
@RequestMapping("api/consume-record")
@Api(value = "消费记录API", description = "消费记录API")
public class ConsumeRecordController {

    @Autowired
    private ConsumeRecordService consumeRecordService;

    @Autowired
    private UserService userService;

    @Autowired
    private DeptService deptService;

    @Autowired
    private PosService posService;

    private static final BigDecimal Tax_Rate = BigDecimal.valueOf(1.06);


    @RequestMapping(value = "/trainingStaffRecord", method = RequestMethod.POST)
    @ApiOperation(value = "训练局职工消费记录", httpMethod = "POST")
    public ResponseData<ConsomeRecordRespDto> trainingStaffRecord(@RequestBody TrainingStaffConsumeRecordDto trainingStaffConsumeRecordDto) {

        // 获取用户ID集合
        List<Integer> userIdList = getUserIdListByConsumeIdentity(trainingStaffConsumeRecordDto.getDeptId(), ConsumeIdentity.TB_STAFF,true);

        if(CollectionUtils.isEmpty(userIdList)){
            return ResponseData.OK(getEmptyConsomerRecordResp());
        }

        // 获取POS机编号集合
        List<String> posNumList = getPosNumList(trainingStaffConsumeRecordDto.getPosPositionId());

        FinanceConsumeRecordDto financeConsumeRecordDto = new FinanceConsumeRecordDto();
        financeConsumeRecordDto.setUserIdList(userIdList);
        financeConsumeRecordDto.setPosNumList(posNumList);
        financeConsumeRecordDto.setStartTime(trainingStaffConsumeRecordDto.getStartTime());
        financeConsumeRecordDto.setEndTime(trainingStaffConsumeRecordDto.getEndTime());
        financeConsumeRecordDto.setCurrentPage(trainingStaffConsumeRecordDto.getCurrentPage());
        financeConsumeRecordDto.setPageSize(trainingStaffConsumeRecordDto.getPageSize());

        return ResponseData.OK(getConsomeRecordRespDto(financeConsumeRecordDto));
    }

    @RequestMapping(value = "/notTrainingStaffRecord", method = RequestMethod.POST)
    @ApiOperation(value = "非训练局职工消费记录", httpMethod = "POST")
    public ResponseData<ConsomeRecordRespDto> notTrainingStaffRecord(@RequestBody NotTrainingStaffConsumeRecordDto notTrainingStaffConsumeRecordDto) {

        // 获取用户ID集合
        List<Integer> userIdList = getUserIdListByConsumeIdentity(notTrainingStaffConsumeRecordDto.getDeptId(),ConsumeIdentity.TB_STAFF,true);

        if(CollectionUtils.isEmpty(userIdList)){
            return ResponseData.OK(getEmptyConsomerRecordResp());
        }

        FinanceConsumeRecordDto financeConsumeRecordDto = new FinanceConsumeRecordDto();
        financeConsumeRecordDto.setUserIdList(userIdList);
        financeConsumeRecordDto.setConsomeType(notTrainingStaffConsumeRecordDto.getConsomeType());
        financeConsumeRecordDto.setStartTime(notTrainingStaffConsumeRecordDto.getStartTime());
        financeConsumeRecordDto.setEndTime(notTrainingStaffConsumeRecordDto.getEndTime());
        financeConsumeRecordDto.setCurrentPage(notTrainingStaffConsumeRecordDto.getCurrentPage());
        financeConsumeRecordDto.setPageSize(notTrainingStaffConsumeRecordDto.getPageSize());

        return ResponseData.OK(getConsomeRecordRespDto(financeConsumeRecordDto));
    }

    @RequestMapping(value = "/securityRecord", method = RequestMethod.POST)
    @ApiOperation(value = "保安消费记录", httpMethod = "POST")
    public ResponseData<ConsomeRecordRespDto> securityRecord(@RequestBody PropertyConsumeRecordDto propertyConsumeRecordDto) {

        // 获取用户ID集合
        List<Integer> userIdList = getUserIdListByConsumeIdentity(null,ConsumeIdentity.PAC,true);

        if(CollectionUtils.isEmpty(userIdList)){
            return ResponseData.OK(getEmptyConsomerRecordResp());
        }

        FinanceConsumeRecordDto financeConsumeRecordDto = new FinanceConsumeRecordDto();
        financeConsumeRecordDto.setUserIdList(userIdList);
        financeConsumeRecordDto.setStartTime(propertyConsumeRecordDto.getStartTime());
        financeConsumeRecordDto.setEndTime(propertyConsumeRecordDto.getEndTime());
        financeConsumeRecordDto.setCurrentPage(propertyConsumeRecordDto.getCurrentPage());
        financeConsumeRecordDto.setPageSize(propertyConsumeRecordDto.getPageSize());

        return ResponseData.OK(getConsomeRecordRespDto(financeConsumeRecordDto));
    }

    @RequestMapping(value = "/cleaningRecord", method = RequestMethod.POST)
    @ApiOperation(value = "保洁消费记录", httpMethod = "POST")
    public ResponseData<ConsomeRecordRespDto> cleaningRecord(@RequestBody PropertyConsumeRecordDto propertyConsumeRecordDto) {

        // 获取用户ID集合
        List<Integer> userIdList = getUserIdListByConsumeIdentity(null,ConsumeIdentity.GD,true);

        if(CollectionUtils.isEmpty(userIdList)){
            return ResponseData.OK(getEmptyConsomerRecordResp());
        }

        FinanceConsumeRecordDto financeConsumeRecordDto = new FinanceConsumeRecordDto();
        financeConsumeRecordDto.setUserIdList(userIdList);
        financeConsumeRecordDto.setStartTime(propertyConsumeRecordDto.getStartTime());
        financeConsumeRecordDto.setEndTime(propertyConsumeRecordDto.getEndTime());
        financeConsumeRecordDto.setCurrentPage(propertyConsumeRecordDto.getCurrentPage());
        financeConsumeRecordDto.setPageSize(propertyConsumeRecordDto.getPageSize());

        return ResponseData.OK(getConsomeRecordRespDto(financeConsumeRecordDto));
    }

    @RequestMapping(value = "/searchUserConsumeRecordDtos", method = RequestMethod.POST)
    @ApiOperation(value = "查询人员消费记录", httpMethod = "POST")
    public ResponseData<IPage<ConsumeRecordDto>> searchUserConsumeRecordDtos(@RequestBody SearchConsumeRecordDto searchConsumeRecordDto) {
        IPage<ConsumeRecordDto> result=  consumeRecordService.searchUserConsumeRecordDtos(searchConsumeRecordDto);
        return ResponseData.OK(result);
    }


    private List<Integer> getUserIdListByConsumeIdentity(Integer deptId,ConsumeIdentity consumeIdentity,boolean isLevle1) {

        if(deptId != null){
            List<Integer> userIdList = Lists.newArrayList();
            List<User> userList  = userService.getUserListByDeptId(deptId);
            if(!CollectionUtils.isEmpty(userList)){
                userIdList = Lists.transform(userList, new Function<User, Integer>() {
                    @Override
                    public Integer apply(User user) {
                        return user.getId();
                    }
                });
            }
            return userIdList;
        }

        List<Integer> level2DeptIdList = Lists.newArrayList();

        if(!isLevle1){
            GetLevel2DeptDto getLevel2DeptDto = new GetLevel2DeptDto();
            getLevel2DeptDto.setConsumeIdentity(consumeIdentity.getCode());

            List<DeptDto> level2DeptList = deptService.getLevel2Dept(getLevel2DeptDto);

            if(!CollectionUtils.isEmpty(level2DeptList)){
                level2DeptIdList = Lists.transform(level2DeptList, new Function<DeptDto, Integer>() {
                    @Override
                    public Integer apply(DeptDto deptDto) {
                        return deptDto.getId();
                    }
                });

            }
            return getUserIdListByLeve2DeptIdList(level2DeptIdList);
        }


        GetLevel1DeptDto getLevel1DeptDto = new GetLevel1DeptDto();
        getLevel1DeptDto.setConsumeIdentity(consumeIdentity.getCode());
        List<DeptDto> deptList = deptService.getLevel1Dept(getLevel1DeptDto);

        if (!CollectionUtils.isEmpty(deptList)) {
            List<Integer> level1DeptIdList = Lists.transform(deptList, new Function<DeptDto, Integer>() {
                @Override
                public Integer apply(DeptDto deptDto) {
                    return deptDto.getId();
                }
            });
            List<List<DeptDto>> level2DeptList = Lists.transform(level1DeptIdList, new Function<Integer, List<DeptDto>>() {
                @Override
                public List<DeptDto> apply(Integer level1DeptId) {
                    AddLevel2DeptDto addLevel2DeptDto = new AddLevel2DeptDto();
                    addLevel2DeptDto.setId(level1DeptId);
                    return deptService.getLevel2Dept(addLevel2DeptDto);
                }
            });
            if (!CollectionUtils.isEmpty(level2DeptList)) {

                for (List<DeptDto> list : level2DeptList) {
                    List<Integer> level2DeptIds = Lists.transform(list, new Function<DeptDto, Integer>() {
                        @Override
                        public Integer apply(DeptDto deptDto) {
                            return deptDto.getId();
                        }
                    });
                    level2DeptIdList.addAll(level2DeptIds);
                }
            }

        }
        return getUserIdListByLeve2DeptIdList(level2DeptIdList);


    }

    private List<String> getPosNumList(Integer posPositionId){
        List<String> posNumList = Lists.newArrayList();
        if(posPositionId != null){
            SearchPosDto searchPosDto = new SearchPosDto();
            searchPosDto.setPosPosition(posPositionId);

            List<Pos> posList = posService.getPosByQuery(searchPosDto);

            if(!CollectionUtils.isEmpty(posList)){
                posNumList = Lists.transform(posList, new Function<Pos, String>() {
                    @Override
                    public String apply(Pos pos) {
                        return pos.getPosNum();
                    }
                });
            }
        }
        return posNumList;
    }

    private List<Integer> getUserIdListByLeve2DeptIdList(List<Integer> level2DeptIdList){

        List<Integer> userIdList = Lists.newArrayList();

        if(!CollectionUtils.isEmpty(level2DeptIdList)){
            List<List<User>> userList = Lists.transform(level2DeptIdList, new Function<Integer, List<User>>() {
                @Override
                public List<User> apply(Integer leve2DeptId) {
                    return userService.getUserListByDeptId(leve2DeptId);
                }
            });
            if (!CollectionUtils.isEmpty(userList)) {
                for (List<User> list : userList) {
                    List<Integer> userIds = Lists.transform(list, new Function<User, Integer>() {
                        @Override
                        public Integer apply(User user) {
                            return user.getId();
                        }
                    });
                    userIdList.addAll(userIds);
                }
            }
        }

        return userIdList;
    }

    private ConsomeRecordRespDto getConsomeRecordRespDto(FinanceConsumeRecordDto financeConsumeRecordDto){

        ConsomeRecordRespDto consomeRecordRespDto = new ConsomeRecordRespDto();
        IPage<ConsumeRecordDto> consumeRecordDtoPage = consumeRecordService.searchFinanceConsumeRecordDtos(financeConsumeRecordDto);

        BigDecimal currentPageAmount = BigDecimal.ZERO;
        BigDecimal currentPageAfterTaxAmount = BigDecimal.ZERO;

        if(consumeRecordDtoPage != null && !CollectionUtils.isEmpty(consumeRecordDtoPage.getRecords())){
            List<ConsumeRecordDto> consumeRecordDtoList = consumeRecordDtoPage.getRecords();
            for(ConsumeRecordDto consumeRecordDto : consumeRecordDtoList){
                currentPageAmount = currentPageAmount.add(consumeRecordDto.getRechargeAmount()).add(consumeRecordDto.getSubsidyAmount());
            }
            currentPageAfterTaxAmount = currentPageAmount.divide(Tax_Rate,BigDecimal.ROUND_HALF_UP);

        }

        BigDecimal totalAmount = consumeRecordService.totalConsomeRecordAmount(financeConsumeRecordDto);
        BigDecimal totalAfterTaxAmount = totalAmount.divide(Tax_Rate,BigDecimal.ROUND_HALF_UP);

        consomeRecordRespDto.setConsumeRecordDtoPage(consumeRecordDtoPage);
        consomeRecordRespDto.setCurrentPageAmount(currentPageAmount);
        consomeRecordRespDto.setCurrentPageAfterTaxAmount(currentPageAfterTaxAmount);
        consomeRecordRespDto.setTotalAmount(totalAmount);
        consomeRecordRespDto.setTotalAfterTaxAmount(totalAfterTaxAmount);
        consomeRecordRespDto.setConsumeRecordDtoPage(consumeRecordDtoPage);

        return consomeRecordRespDto;
    }

    private ConsomeRecordRespDto getEmptyConsomerRecordResp(){
        ConsomeRecordRespDto consomeRecordRespDto = new ConsomeRecordRespDto();
        consomeRecordRespDto.setConsumeRecordDtoPage(new Page<>());
        return consomeRecordRespDto;
    }
}
