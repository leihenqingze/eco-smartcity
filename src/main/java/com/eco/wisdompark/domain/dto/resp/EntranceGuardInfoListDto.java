package com.eco.wisdompark.domain.dto.resp;

import com.eco.wisdompark.domain.dto.req.JsLife.SearchEntranceGuardCardInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.List;

 /**
  * 人员拥有的门禁信息集合
  *
  * @author zhangkai
  * @date 2019/5/19 下午3:50
  */
@Data
@ApiModel(value = "查询门禁信息返回Dto", description = "查询门禁信息返回Dto")
public class EntranceGuardInfoListDto {

     @ApiModelProperty(value = "门号")
     private String doorId;

     @ApiModelProperty(value = "门名称")
     private String doorName;

     @ApiModelProperty(value = "卡集合")
     private List<SearchEntranceGuardCardInfo> cardList;
}
