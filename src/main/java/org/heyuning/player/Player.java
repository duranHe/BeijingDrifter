package org.heyuning.player;

import org.heyuning.product.Inventory;

import java.util.HashMap;
import java.util.Map;

import static org.heyuning.util.Constant.*;

public class Player {
    public int cash;
    public int saving;
    public int debt;
    public int health;
    public int fame;

    public int load;
    public int room;
    public Map<String, Inventory> inventories;

    public Player() {
        cash = START_CASH;
        saving = START_SAVING;
        debt = START_DEBT;
        health = START_HEALTH;
        fame = START_FAME;
        load = 0;
        room = START_ROOM;
        inventories = new HashMap<>();
    }

    /**
     *
     * @param productName
     * @param cost
     * @param amount
     * @return true if it is a new product, false if it is an old product
     */
    public boolean buyProduct(String productName, int cost, int amount) {
        cash -= cost * amount;
        load += amount;

        if(inventories.containsKey(productName)) {
            Inventory inventory = inventories.get(productName);
            inventory.buy(cost, amount);
            return false;
        } else {
            inventories.put(productName, new Inventory(productName, cost, amount));
            return true;
        }
    }

    /**
     *
     * @param productName
     * @param price
     * @param amount
     * @return true if sell all, false not sell all
     */
    public boolean sellProduct(String productName, int price, int amount) {
        load -= amount;

        Inventory inventory = inventories.get(productName);
        if(amount >= inventory.amount) {
            cash += inventory.amount * price;
            inventories.remove(productName);
            return true;
        } else {
            cash += amount * price;
            inventory.amount -= amount;
            return false;
        }
    }
}
