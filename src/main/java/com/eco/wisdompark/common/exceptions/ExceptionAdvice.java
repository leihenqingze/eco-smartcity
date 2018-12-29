package com.eco.wisdompark.common.exceptions;

import com.eco.wisdompark.common.dto.ResponseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理
 */
@Component
@ControllerAdvice
@Slf4j
public class ExceptionAdvice {

    // 业务异常
    @ExceptionHandler(value = BaseException.class)
    @ResponseBody
    public ResponseData handleBaseException(BaseException e) {
        log.error("[业务异常]", e);
        int status = e.getStatus() != 0 ? e.getStatus() : 400;
        return handleAjaxRequestException(e, status);
    }

    // 客户端输入异常
    @ExceptionHandler(value = IllegalArgumentException.class)
    @ResponseBody
    public ResponseData handleException(IllegalArgumentException e) {
        log.error("[参数错误]", e);
        return handleAjaxRequestException(e, 400);
    }

    // 服务端异常
    @ExceptionHandler(value = Throwable.class)
    @ResponseBody
    public ResponseData handleException(Throwable e) {
        log.error("[其他]", e);
        return handleAjaxRequestException("系统内部异常,请联系管理员", 500);
    }

    private ResponseData handleAjaxRequestException(Throwable e, int code) {
        return new ResponseData(code, e.getMessage());
    }

    private ResponseData handleAjaxRequestException(String message, int code) {
        return new ResponseData(code, message);
    }

}
