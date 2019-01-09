package com.eco.wisdompark.common.utils;

public class StringTools {

    /**
     * 将卡编号由十进制转为十六进制
     * @param decimal 十进制卡编号
     * @return 十六进制卡编号
     */
    public static String cardDecimalToHexString(String decimal) {
        String num = Long.toHexString(Long.valueOf(decimal));
        char[] codes = num.toCharArray();
        char[] result = new char[codes.length];
        int index = 0;
        for (int i = (codes.length - 1); i >= 0; i -= 2) {
            result[index] = codes[i - 1];
            result[index + 1] = codes[i];
            index += 2;
        }
        String hex = new String(result);
        return hex;
    }

}
