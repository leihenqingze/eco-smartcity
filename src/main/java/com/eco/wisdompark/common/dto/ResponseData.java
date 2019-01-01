package com.eco.wisdompark.common.dto;

import lombok.Data;

import java.io.Serializable;


/**
 * ajax请求返回结果
 *
 * @author litao
 */
@Data
public class ResponseData<T> implements Serializable {
    private static final long serialVersionUID = 8125672939123850928L;

    public static final int STATUS_CODE_200 = 200;
    public static final int STATUS_CODE_400 = 400;
    public static final int STATUS_CODE_403 = 403;
    public static final int STATUS_CODE_404 = 404;
    public static final int STATUS_CODE_500 = 500;

    public static final int STATUS_CODE_110 = 110; // 登录已过期

    public static final int STATUS_CODE_410 = 410; // Gone
    public static final int STATUS_CODE_412 = 412; // 未满足前提条件
    public static final int STATUS_CODE_422 = 422; // 请求格式正确，但是由于含有语义错误，无法响应。
    public static final int STATUS_CODE_423 = 423; // 当前资源被锁定。

    public static final int STATUS_CODE_461 = 461; // 验证码错误
    public static final int STATUS_CODE_462 = 462; // 无订单商品
    public static final int STATUS_CODE_463 = 463; // 组织架构名称已经存在
    public static final int STATUS_CODE_464 = 464; // 未匹配到消费类型
    public static final int STATUS_CODE_465 = 465; // 请输入组织架构名称
    public static final int STATUS_CODE_467 = 467; // 组织架构下存在人员无法删除
    public static final int STATUS_CODE_468 = 468; // 存在二级组织架构无法删除

    public static final int STATUS_CODE_600 = 600; // 用户已存在
    public static final int STATUS_CODE_601 = 601; // 用户或卡信息不存在
    public static final int STATUS_CODE_602 = 602; // 卡ID或卡序列号已存在

    public static final String STATUS_MESSAGE_200 = "OK";
    public static final String STATUS_MESSAGE_400 = "Error";

    public static ResponseData OK() {
        return new ResponseData(STATUS_CODE_200, STATUS_MESSAGE_200);
    }

    public static ResponseData OK(Object data) {
        return new ResponseData(STATUS_CODE_200, STATUS_MESSAGE_200, data);
    }

    public static ResponseData ERROR(int code) {
        return new ResponseData(code, STATUS_MESSAGE_400);
    }

    public static ResponseData ERROR(Object data) {
        return new ResponseData(STATUS_CODE_400, STATUS_MESSAGE_400, data);
    }

    public static ResponseData ERROR(int code, String message) {
        return new ResponseData(code, message);
    }

    public static ResponseData ERROR(String message) {
        return new ResponseData(STATUS_CODE_400, message);
    }

    public static ResponseData ERROR() {
        return new ResponseData(STATUS_CODE_400, STATUS_MESSAGE_400);
    }

    private int code;
    private String message;
    private T data;

    public ResponseData() {
        code = STATUS_CODE_200;
        message = STATUS_MESSAGE_200;
    }

    public ResponseData(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResponseData(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ResponseData(T data) {
        this();
        this.data = data;
    }

}
