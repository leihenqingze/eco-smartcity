package com.eco.wisdompark.domain.dto.req.consumeRecord;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.List;

@Data
@ApiModel(value="财务统计消费记录", description="财务统计消费记录")
public class FinanceConsumeRecordDto {


    @ApiModelProperty(value = "人员ID集合")
    private List<Integer> userIdList;

    @ApiModelProperty(value = "pos机编号集合")
    private List<String> posNumList;

    @ApiModelProperty(value = "消费类型，0：用餐，1：购物")
    private Integer consomeType;

    @ApiModelProperty(value = "用餐类型，0：早餐，1：午餐，2：晚餐")
    private List<Integer> diningTypeList;

    @ApiModelProperty(value = "起始时间")
    private String startTime;

    @ApiModelProperty(value = "结束时间")
    private String endTime;

    @ApiModelProperty(value = "当前页")
    private  Integer currentPage=0;

    @ApiModelProperty(value = "每页长度")
    private Integer pageSize=20;



}
