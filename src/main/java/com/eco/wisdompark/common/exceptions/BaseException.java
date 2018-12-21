package com.eco.wisdompark.common.exceptions;

import com.eco.wisdompark.common.utils.MessageUtils;
import org.springframework.util.StringUtils;


/**
 * 方便做日志的一个异常
 *
 * @author frank
 */
@SuppressWarnings("serial")
public class BaseException extends RuntimeException {

    // 所属模块
    private String module;

    /**
     * 状态码，给外部程序提供接口状态
     */
    private int status;

    /**
     * 错误码
     */
    private String code;

    /**
     * 错误码对应的参数
     */
    private Object[] args;

    /**
     * 默认错误消息
     */
    private String defaultMessage;

    /**
     * 错误信息
     */
    private String msg;

    public BaseException(String module, String code, Object[] args, String defaultMessage) {
        this.module = module;
        this.code = code;
        this.args = args;
        this.defaultMessage = defaultMessage;
    }

    public BaseException(int status, String msg) {
        super();
        this.status = status;
        this.msg = msg;
    }

    public BaseException(String module, String code, Object[] args) {
        this(module, code, args, null);
    }

    public BaseException(String module, String defaultMessage) {
        this(module, null, null, defaultMessage);
    }

    public BaseException(String code, Object[] args) {
        this(null, code, args, null);
    }

    @Override
    public String getMessage() {
        String message = null;
        if (!StringUtils.isEmpty(code)) {
            message = MessageUtils.message(code, args);
        }
        if (message == null) {
            message = defaultMessage;
        }
        if (message == null) {
            message = msg;
        }
        return message;
    }

    public String getModule() {
        return module;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public Object[] getArgs() {
        return args;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }

    @Override
    public String toString() {
        return this.getClass() + "{" + "module='" + module + '\'' + ", message='" + getMessage() + '\'' + '}';
    }
}
