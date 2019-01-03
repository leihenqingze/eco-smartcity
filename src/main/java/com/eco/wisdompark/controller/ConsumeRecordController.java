package com.eco.wisdompark.controller;


import com.eco.wisdompark.common.dto.ResponseData;
import com.eco.wisdompark.domain.dto.req.dept.AddLevel2DeptDto;
import com.eco.wisdompark.domain.dto.req.dept.DeptDto;
import com.eco.wisdompark.domain.dto.req.dept.GetLevel1DeptDto;
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
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
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


    @RequestMapping(value = "/trainingStaffRecord", method = RequestMethod.GET)
    @ApiOperation(value = "训练局职工消费记录", httpMethod = "GET")
    public ResponseData<ConsomeRecordRespDto> trainingStaffRecord(@Param("deptId") Integer deptId,
                                                                  @Param("posPositionId") Integer posPositionId,
                                                                  @Param("startTime") String startTime,
                                                                  @Param("endTime") String endTime) {

        // 获取用户ID集合
        List<Integer> userIdList = getUserIdListByConsumeIdentity(deptId,ConsumeIdentity.TB_STAFF,true);

        // 获取POS机编号集合
        List<String> posNumList = getPosNumList(posPositionId);


        return ResponseData.OK();
    }

    @RequestMapping(value = "/notTrainingStaffRecord", method = RequestMethod.GET)
    @ApiOperation(value = "非训练局职工消费记录", httpMethod = "GET")
    public ResponseData<ConsomeRecordRespDto> notTrainingStaffRecord(@Param("deptId") Integer deptId,
                                                                     @Param("consomeType") Integer consomeType,
                                                                     @Param("startTime") String startTime,
                                                                     @Param("endTime") String endTime) {

        // 获取用户ID集合
        List<Integer> userIdList = getUserIdListByConsumeIdentity(deptId,ConsumeIdentity.TB_STAFF,true);

        return ResponseData.OK();
    }

    @RequestMapping(value = "/securityRecord", method = RequestMethod.GET)
    @ApiOperation(value = "保安消费记录", httpMethod = "GET")
    public ResponseData<ConsomeRecordRespDto> securityRecord(@Param("startTime") String startTime,
                                                             @Param("endTime") String endTime) {

        // 获取用户ID集合
        List<Integer> userIdList = getUserIdListByConsumeIdentity(null,ConsumeIdentity.PAC,false);

        return ResponseData.OK();
    }

    @RequestMapping(value = "/cleaningRecord", method = RequestMethod.GET)
    @ApiOperation(value = "保洁消费记录", httpMethod = "GET")
    public ResponseData<ConsomeRecordRespDto> cleaningRecord(@Param("startTime") String startTime,
                                                             @Param("endTime") String endTime) {

        // 获取用户ID集合
        List<Integer> userIdList = getUserIdListByConsumeIdentity(null,ConsumeIdentity.GD,false);

        return ResponseData.OK();
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
            AddLevel2DeptDto addLevel2DeptDto = new AddLevel2DeptDto();
            addLevel2DeptDto.setId(ConsumeIdentity.PROPERTY.getCode());
            addLevel2DeptDto.setConsumeIdentity(consumeIdentity.getCode());

            List<DeptDto> level2DeptList = deptService.getLevel2Dept(addLevel2DeptDto);

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
}
