package org.klimenko;

import java.util.HashMap;
import java.util.Map;

public class Parser {
        public static Map<String, Object> ParsingMoney(String[] strings) throws WrongFormatException {
            HashMap<String, Object> map = new HashMap<>();
            map.put("name", " ");
            map.put("amount", " ");
            if(strings[0].startsWith("@")) {
                map.put("name", strings[0].substring(1));
                map.put("amount", Double.parseDouble(strings[1]));
            }
            if (strings[1].startsWith("@")) {
                map.put("name", strings[1].substring(1));
                map.put("amount", Double.parseDouble(strings[0]));
                //System.out.println(map.get("amount"));
            }
            if ((double)map.get("amount") < 0) {
                throw new WrongFormatException("Value can't be less than zero");
            }
            return map;
        }
}
