package com.eco.wisdompark.enums;


 /**
  * 性别
  *
  * @author zhangkai
  * @date 2019/3/24 下午3:53
  */
public enum Gender implements CommonEnum<Gender> {
    MAN(0, "男"),
    WOMEN(1, "女");

    private final int code;
    private final String description;

    Gender(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static Gender valueOf(int code) {
        for (Gender item : Gender.values()) {
            if (item.getCode() == code) {
                return item;
            }
        }
        throw new IllegalArgumentException("性别不支持");
    }


}