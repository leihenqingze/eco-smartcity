package com.eco.wisdompark.domain.dto.req.card;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.sf.oval.constraint.Length;
import net.sf.oval.constraint.MemberOf;
import net.sf.oval.constraint.NotNull;

@Data
@ApiModel(value = "启停卡片Dto", description = "启停卡片Dto")
public class StartStopDto {

    @ApiModelProperty(value = "CPU卡物理Id")
    @NotNull(message = "CPU卡信息未读取成功")
    @Length(max = 50, message = "CPU卡物理Id长度不能超过50位")
    private String cardId;

    @ApiModelProperty(value = "是否停用：0启动,1停用")
    @NotNull(message = "是否停用不能为空")
    @MemberOf(value = {"0", "1"}, message = "是否停用只能是0、1")
    private int ifUsed;

}
