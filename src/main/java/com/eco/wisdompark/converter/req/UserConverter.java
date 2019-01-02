package com.eco.wisdompark.converter.req;

import com.eco.wisdompark.domain.model.User;
import com.eco.wisdompark.enums.YesNo;

import java.time.LocalDateTime;

/**
 * 人员信息转换类
 */
public class UserConverter {

    public static User create(String userName, String userCardNum, Integer deptId, String phoneNum){
        User user = new User();
        user.setUserName(userName);
        user.setUserCardNum(userCardNum);
        user.setDeptId(deptId);
        user.setPhoneNum(phoneNum);
        user.setDel(YesNo.NO.getCode());
        return user;
    }
}
