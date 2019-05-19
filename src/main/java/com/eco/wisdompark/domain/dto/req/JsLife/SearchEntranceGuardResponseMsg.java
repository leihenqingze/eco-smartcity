package com.eco.wisdompark.domain.dto.req.JsLife;

import lombok.Data;
import lombok.ToString;
import java.util.List;

/**
  * 查询门禁业务返回报文信息
  *
  * @author zhangkai
  * @date 2019/5/19 下午3:28
  */
@Data
@ToString
public class SearchEntranceGuardResponseMsg {

    private int resultCode; // 0：无异常，

    private String message; // 异常信息

    private String serviceId; // 服务标识

    private List<SearchEntranceGuardDataItem> dataItems; // 门禁数据集合
}
