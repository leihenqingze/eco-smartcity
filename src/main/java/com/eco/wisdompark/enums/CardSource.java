package com.eco.wisdompark.enums;


/**
 * @author litao, 2018/12/29
 * @version 1.0
 */
public enum CardSource implements CommonEnum<CardSource> {
    MAKE_CARD(0, "制卡"),
    ACTIVATION(1, "激活");

    private final int code;
    private final String description;

    CardSource(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static CardSource valueOf(int code) {
        for (CardSource item : CardSource.values()) {
            if (item.getCode() == code) {
                return item;
            }
        }
        throw new IllegalArgumentException("当前的CPU卡来源暂不支持");
    }


}