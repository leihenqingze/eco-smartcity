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
    @Min(value = 0, message = "分页大小大于0")
    @Max(value = 1000, message = "分页大小不能大于1000")
    @ApiModelProperty(value = "分页大小")
    private long size;
    @NotNull
    @Min(value = 1, message = "当前页必须大于1")
    @ApiModelProperty(value = "当前页")
    private long current;
    @ApiModelProperty(value = "查询对象")
    private T query;

}
