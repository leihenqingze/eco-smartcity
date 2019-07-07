package com.eco.wisdompark.common.utils;

import java.util.HashMap;
import java.util.Map;

public class ConsumeIdentityUtils {

    public static String getConsumeIdentityUtils(Integer key){
        Map<Integer,String> map =new HashMap<>();
        map.put(0,"消费身份A:早餐5元,午餐、晚餐正常消费20，从第3次开始收费为29");
        map.put(1,"消费身份B:早餐6元,午餐、晚餐正常消费20，从第2次开始收费为29");
        map.put(2,"消费身份C:早餐5元,午餐、晚餐免费，午餐加晚餐只能消费一次");
        map.put(3,"消费身份D:早餐5元,午餐免费，只能消费一次.晚餐免费，只能消费一次");
        map.put(4,"消费身份E:早餐5元,午餐、晚餐正常消费20，从第3次开始收费为29,晚餐5元");
        map.put(5,"消费身份F:一日三餐都免费，每餐只能消费一次");
        return map.get(key);
    }


}
