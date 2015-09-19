package com.kitchen_ehhd.Models;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by vishalkuo on 15-09-19.
 */
public class MockSearchItems {
    private Map<String, Integer> itemToDrawerMap = new HashMap<>();

    public MockSearchItems() {
        itemToDrawerMap.put("Fork", 1);
        itemToDrawerMap.put("Spoon", 3);
        itemToDrawerMap.put("Pepper", 2);
        itemToDrawerMap.put("Salt", 2);
        itemToDrawerMap.put("Tree", 1);
    }

    public Map<String, Integer> getItemToDrawerMap() {
        return itemToDrawerMap;
    }
}
