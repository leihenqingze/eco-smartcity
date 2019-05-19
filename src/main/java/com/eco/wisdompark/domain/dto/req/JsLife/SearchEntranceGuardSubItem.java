package com.eco.wisdompark.domain.dto.req.JsLife;

import lombok.Data;
import lombok.ToString;

/**
 * 查询门禁返回卡数据
 *
 * @author zhangkai
 * @date 2019/5/19 下午3:28
 */
@Data
@ToString
public class SearchEntranceGuardSubItem{

    private String objectId; // 子对象ID

    private String operateType; // 子对象操作类型

    private SearchEntranceGuardCardInfo attributes; // 卡信息
}