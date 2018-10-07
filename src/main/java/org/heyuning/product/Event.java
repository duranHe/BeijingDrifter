package org.heyuning.product;

import java.util.Random;

public class Event {
    public String description;
    public double multiplier;

    public Event(String description, double multiplier) {
        this.description = description;
        this.multiplier = multiplier;
    }

    public double getProbablity() {
        return new Random().nextDouble();
    }
}
