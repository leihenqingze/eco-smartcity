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
    public static final int STATUS_CODE_469 = 469; // 余额不足
    public static final int STATUS_CODE_470 = 470; // 该时间段不可以用餐
    public static final int STATUS_CODE_471 = 471; // 消费次数超过限制

    public static final int STATUS_CODE_600 = 600; // 用户已存在
    public static final int STATUS_CODE_601 = 601; // 用户或卡信息不存在
    public static final int STATUS_CODE_602 = 602; // 卡ID或卡序列号已存在
    public static final int STATUS_CODE_603 = 603; // 批量充值文件不能为空
    public static final int STATUS_CODE_604 = 604; // 批量充值处理异常
    public static final int STATUS_CODE_605 = 605; // 文件格式不正确
    public static final int STATUS_CODE_606 = 606; // 文件读取异常
    public static final int STATUS_CODE_607 = 607; // 文件code不能为空
    public static final int STATUS_CODE_608 = 608; // 批量充值最大限制200条
    public static final int STATUS_CODE_609 = 609; // 未绑卡
    public static final int STATUS_CODE_610 = 610; // 验证码不正确
    public static final int STATUS_CODE_611 = 611; // 用户不存在
    public static final int STATUS_CODE_612 = 612; // 批量制卡文件不能为空
    public static final int STATUS_CODE_613 = 613; // 数据库操作异常

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
