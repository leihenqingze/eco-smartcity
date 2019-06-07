package com.eco.wisdompark.enums;

 /**
  * 充值方式
  *
  * @author zhangkai
  * @date 2019/6/7 下午3:16
  */
public enum RechargeWay implements CommonEnum<RechargeWay> {

    CASH_DEPOSIT(1, "现金存款"),

    CHEQUE_IMPORT(2, "支票存款"),

    OLD_IMPORT(3,"旧卡导入"),

    Remittance(4,"汇款");

    private final int code;

    private final String description;

    RechargeWay(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static RechargeWay valueOf(int code) {
        for (RechargeWay item : RechargeWay.values()) {
            if (item.getCode() == code) {
                return item;
            }
        }
        throw new IllegalArgumentException("未知的");
    }

     public static RechargeWay getByDescription(String description) {
         for (RechargeWay item : RechargeWay.values()) {
             if (item.getDescription().equals(description)) {
                 return item;
             }
         }
         return null;
     }

}
