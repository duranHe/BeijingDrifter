package org.heyuning.product;

public class Inventory {
    String productName;
    int cost;
    int amount;

    public Inventory(String productName, int cost, int amount) {
        this.productName = productName;
        this.cost = cost;
        this.amount = amount;
    }

    public void update(int cost, int amount) {
        int totalSpend = this.cost * this.amount + cost * amount;
        this.cost = 
    }
}
