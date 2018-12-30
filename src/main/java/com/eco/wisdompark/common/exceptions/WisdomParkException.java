package com.eco.wisdompark.common.exceptions;

import org.apache.commons.lang3.StringUtils;

public class WisdomParkException extends RuntimeException {
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
