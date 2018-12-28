package com.eco.wisdompark.enums;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.JSONSerializable;
import com.alibaba.fastjson.serializer.JSONSerializer;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * @author 陈伟, chenwei@maichengvip.com on 2016/12/8
 * @version 1.0
 */
public enum YesNo implements JSONSerializable, CommonEnum<YesNo> {
    YES(0, "是"),
    NO(1, "否");

    private final int code;
    private final String description;

    YesNo(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static YesNo valueOf(int code) {
        for (YesNo yesNo : YesNo.values()) {
            if (yesNo.getCode() == code) {
                return yesNo;
            }
        }
        throw new IllegalArgumentException("未知的");
    }

    @Override
    public void write(JSONSerializer serializer, Object fieldName, Type fieldType, int features)
            throws IOException {
        JSONObject json = new JSONObject();
        json.put("code", code);
        json.put("description", description);
        serializer.write(json);
    }

}
