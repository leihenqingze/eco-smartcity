package com.eco.wisdompark.domain.dto.req.JsLife;

import lombok.Data;
import lombok.ToString;

/**
 * 开门请求业务参数
 *
 * @author zhangkai
 * @date 2019年05月19日 17:58
 */
@Data
@ToString
public class OpenDoorBusinessDto {

    private String cardId; // 卡ID

    private String doorId; // 门号
}
