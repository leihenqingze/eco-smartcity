package com.eco.wisdompark.domain.dto.req.JsLife;

import lombok.Data;
import lombok.ToString;

/**
 * 捷汇通云平台请求通用参数
 *
 * @author zhangkai
 * @date 2019年05月18日 20:54
 */
@Data
@ToString
public class CommonRequestParam {

    private String serviceId; // 服务标识

    private String requestType; // 请求类型

    private Object attributes; // 业务参数

}
