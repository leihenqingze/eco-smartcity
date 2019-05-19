package com.eco.wisdompark.domain.dto.req.JsLife;

import lombok.Data;
import lombok.ToString;

/**
 * 查询门禁业务参数
 *
 * @author zhangkai
 * @date 2019年05月18日 20:59
 */
@Data
@ToString
public class SearchEntranceGuardBusinessParam {

    private String areaCode; // 小区编号

    private String personCode; // 人员编号
}
