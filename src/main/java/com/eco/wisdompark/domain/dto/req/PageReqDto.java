package com.eco.wisdompark.domain.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.sf.oval.constraint.Max;
import net.sf.oval.constraint.Min;
import net.sf.oval.constraint.NotNull;

@Data
@ApiModel(value = "分页查询请求对象", description = "分页查询请求对象")
public class PageReqDto<T> {

    @NotNull
    @ApiModelProperty(value = "分页大小")
    private Integer size;
    @NotNull
    @ApiModelProperty(value = "当前页")
    private Integer current;
    @ApiModelProperty(value = "查询对象")
    private T query;

}
