package com.eco.wisdompark.domain.dto.req.JsLife;

import lombok.Data;
import lombok.ToString;

/**
 * 查询门禁返回门信息
 *
 * @author zhangkai
 * @date 2019/5/19 下午3:28
 */
@Data
@ToString
public class SearchEntranceGuardDoorInfo{

    private String doorId; // 门号

    private String doorName; // 门名称
}