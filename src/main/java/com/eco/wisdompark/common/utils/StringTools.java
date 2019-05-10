package com.eco.wisdompark.common.utils;

public class StringTools {

    /**
     * 将卡编号由十进制转为十六进制
     *
     * @param decimal 十进制卡编号
     * @return 十六进制卡编号
     */
    public static String cardDecimalToHexString(String decimal) {
        return hexStringReversal(Long.toHexString(Long.valueOf(decimal)));
    }

    /**
     * 将卡编号由十六进制转为十进制
     *
     * @param hexString 十六进制卡编号
     * @return 十进制卡编号
     */
    public static String cardHexStringToDecimal(String hexString) {
        return Long.valueOf(hexStringReversal(hexString), 16).toString();
    }

    /**
     * 实现十六进制字符串两两反转
     *
     * @param hexString 十六进制字符串
     * @return 反转后的十六进制字符串
     */
    private static String hexStringReversal(String hexString) {
        char[] codes = hexString.toCharArray();
        char[] result = new char[codes.length];
        int index = 0;
        for (int i = (codes.length - 1); i >= 0; i -= 2) {
            result[index] = codes[i - 1];
            result[index + 1] = codes[i];
            index += 2;
        }
        return String.valueOf(result);
    }

    public static void main(String[] args) {
        String sss = cardDecimalToHexString("3843031453");
        String ssss = cardHexStringToDecimal(sss);
        System.out.println(ssss);
    }

}