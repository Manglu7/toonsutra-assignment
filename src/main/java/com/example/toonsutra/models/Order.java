package com.example.toonsutra.models;

import lombok.Getter;
import lombok.Setter;

public class Order {
    @Getter
    private final String orderId;
    @Getter
    private final char side; // 'B' for Buy, 'S' for Sell
    @Getter
    private final int price;
    @Getter
    private final int quantity;

    public Order(String orderId, char side, int price, int quantity) {
        this.orderId = orderId;
        this.side = side;
        this.price = price;
        this.quantity = quantity;
    }
}