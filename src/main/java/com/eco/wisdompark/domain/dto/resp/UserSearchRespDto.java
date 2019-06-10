package com.eco.wisdompark.domain.dto.resp;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.eco.wisdompark.domain.dto.req.user.UserDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel(value = "人员信息统计", description = "人员信息统计")
public class UserSearchRespDto {

    @ApiModelProperty(value = "当前页充值总金额")
    private BigDecimal currentPageRechargeAmount = BigDecimal.ZERO;

    @ApiModelProperty(value = "当前页补助总金额")
    private BigDecimal currentPageSubsidyAmount = BigDecimal.ZERO;

    @ApiModelProperty(value = "充值总金额")
    private BigDecimal totalRechargeAmount = BigDecimal.ZERO;

    @ApiModelProperty(value = "补助总金额")
    private BigDecimal totalSubsidyAmount = BigDecimal.ZERO;

    @ApiModelProperty(value = "分页信息")
    private IPage<UserDto> userDtoIPage;

}
