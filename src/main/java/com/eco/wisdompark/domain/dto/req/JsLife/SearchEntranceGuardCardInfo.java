package com.eco.wisdompark.domain.dto.req.JsLife;

import lombok.Data;
import lombok.ToString;

/**
 * 查询门禁返回卡信息
 *
 * @author zhangkai
 * @date 2019/5/19 下午3:28
 */
@Data
@ToString
public class SearchEntranceGuardCardInfo{

    private String cardId; // 卡ID

    private String physicalNo; // 物理卡号
}