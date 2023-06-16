package org.klimenko;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Parser {
    public static Map<String, Object> ParsingMoney(String[] strings) throws Exception {
        if(strings.length<2){
            throw new Exception("To use this function you have to write command"+
                    "+ sum + name");
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", " ");
        map.put("amount", " ");
        if (strings[0].startsWith("@")) {
            map.put("name", strings[0].substring(1));
            map.put("amount", new BigDecimal(strings[1]));
        }
        if (strings[1].startsWith("@")) {
            map.put("name", strings[1].substring(1));
            map.put("amount", new BigDecimal(strings[0]));
        }
        BigDecimal amountValue = (BigDecimal) map.get("amount");
        if (amountValue.compareTo(BigDecimal.ZERO) < 0) {
            throw new Exception("Value can't be less than 0");
        }
        return map;
    }
}
