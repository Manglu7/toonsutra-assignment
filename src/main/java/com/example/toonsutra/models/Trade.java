package com.example.toonsutra.models;

import lombok.Data;

@Data
public class Trade {
    private final String aggressiveOrderId;
    private final String restingOrderId;
    private final int price;
    private final int quantity;

    public Trade(String aggressiveOrderId, String restingOrderId, int price, int quantity) {
        this.aggressiveOrderId = aggressiveOrderId;
        this.restingOrderId = restingOrderId;
        this.price = price;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return String.format("trade %s,%s,%d,%d", aggressiveOrderId, restingOrderId, price, quantity);
    }
}