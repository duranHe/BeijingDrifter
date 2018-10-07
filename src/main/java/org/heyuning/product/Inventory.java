package org.heyuning.product;

public class Inventory {
    public String productName;
    public int cost;
    public int amount;

    public Inventory(String productName, int cost, int amount) {
        this.productName = productName;
        this.cost = cost;
        this.amount = amount;
    }

    public void buy(int cost, int amount) {
        int totalSpend = this.cost * this.amount + cost * amount;
        int totalAmount = this.amount + amount;

        this.cost = totalSpend / totalAmount;
        this.amount = totalAmount;
    }
}
