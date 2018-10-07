package org.heyuning.player;

import org.heyuning.product.Inventory;

import java.util.HashMap;
import java.util.Map;

public class Player {
    static final int START_CASH = 2000;
    static final int START_DEBT = 5000;
    static final int START_SAVING = 0;
    static final int START_HEALTH = 100;
    static final int START_FAME = 100;

    public int cash;
    public int saving;
    public int debt;
    public int health;
    public int fame;
    public Map<String, Inventory> inventories;

    public Player() {
        cash = START_CASH;
        saving = START_SAVING;
        debt = START_DEBT;
        health = START_HEALTH;
        fame = START_FAME;
        inventories = new HashMap<>();
    }

    public void buyProduct(String productName, int cost, int amount) {
        if(inventories.containsKey(productName)) {
            Inventory inventory = inventories.get(productName);
            inventory.update(cost, amount);
        } else {
            inventories.put(productName, new Inventory(productName, cost, amount));
        }
    }
}
