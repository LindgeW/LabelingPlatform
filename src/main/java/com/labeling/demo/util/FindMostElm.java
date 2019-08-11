package com.labeling.demo.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FindMostElm {
    public String findfinaltag(ArrayList<String> array) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        for (String s : array) {
            if (map.containsKey(s)) {
                int qty = map.get(s);
                map.put(s, qty + 1);
            } else {
                map.put(s, 1);
            }
        }
        int max = 0;
        String temp = "";
        int value = 0;
        for (String key : map.keySet()) {
            value = map.get(key);
            if (max < value) {
                max = value;
                temp = key;
            }
        }
        return temp;

    }

}
