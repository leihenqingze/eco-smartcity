package com.eco.wisdompark.domain.dto.req.JsLife;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * 查询门禁返回门禁数据
 *
 * @author zhangkai
 * @date 2019/5/19 下午3:28
 */
@Data
@ToString
public class SearchEntranceGuardDataItem{

    private String objectId; // 子对象ID

    private String operateType; // 子对象操作类型

    private SearchEntranceGuardDoorInfo attributes; // 门信息

    private List<SearchEntranceGuardSubItem> subItems; // 卡数据集合
}