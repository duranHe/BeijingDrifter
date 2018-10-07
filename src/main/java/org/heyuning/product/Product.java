package org.heyuning.product;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public class Product {
    String name;
    int value;
    int random;
    List<Event> events;

    public Product(String name, int value, int random, List<Event> events) {
        this.name = name;
        this.value = value;
        this.random = random;
        this.events = events;
    }

    public int getPrice() {
        double price = value;
        double range = random;

        Optional<Event> event = getEvent();
        if(event.isPresent()) {
            price *= event.get().multiplier;
            range *= event.get().multiplier;
        }

        Random r = new Random();
        return (int)((r.nextDouble() * 2 - 1) * range + price);
    }

    private Optional<Event> getEvent() {
        double maxProb = 0;
        Event candidate = events.get(0);
        for(Event event : events) {
            double prob = event.getProbablity();
            if(prob > maxProb) {
                candidate = event;
                maxProb = prob;
            }
        }
        return maxProb > 0.5 ? Optional.of(candidate) : Optional.empty();
    }
}
