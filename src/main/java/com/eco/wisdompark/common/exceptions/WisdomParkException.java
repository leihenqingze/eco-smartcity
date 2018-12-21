package com.eco.wisdompark.common.exceptions;

import org.apache.commons.lang3.StringUtils;

/**
 * @author 陈伟, chenwei@maichengvip.com on 2016/12/7
 * @version 1.0
 */
public class WisdomParkException extends Exception {
    private static final long serialVersionUID = 2734616083002297625L;

    private int code;

    private String message;

    public WisdomParkException(int code, Exception e) {
        super(e);
        this.code = code;
    }

    public WisdomParkException(String code, String message) {
        this(Integer.parseInt(code), message);
    }

    public WisdomParkException(int code) {
        this.code = code;
    }

    public WisdomParkException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        if (StringUtils.isNotBlank(this.message)) {
            return message;
        }
        return super.getMessage();
    }
}
