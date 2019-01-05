package com.eco.wisdompark.common.utils;

/**
 * Created by haihao on  2019/1/5.
 */
public class IdCardUtils {

    public static String idCardHidden(String userCardNum){
        String idCardNo = userCardNum.replaceAll("(\\d{4})\\d{10}(\\w{4})","$1******$2");
        return idCardNo;
    }

    public static String mobileHidden(String phoneNum){
        String mobile = phoneNum.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2");
        return mobile;
    }

    public static void main(String[] args) {
        System.out.println("after:" + idCardHidden("370481199208162231"));
        System.out.println("after:" + mobileHidden("13488646855"));
    }
}
