package org.heyuning.product;

import java.util.HashMap;
import java.util.Map;

public class BlackMarket {
    public Map<String, Integer> products;

    public Map<String, Integer> generateProducts() {
        Map<String, Integer> products = new HashMap<>();
        products.put("盗版光盘", 10);
        products.put("婴儿奶粉", 20);
        products.put("水货手机", 100);
        return products;
    }

    public BlackMarket() {
        products = generateProducts();
    }
}
