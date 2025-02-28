package com.example.toonsutra.services;

import com.example.toonsutra.models.Order;
import com.example.toonsutra.models.Trade;

import java.util.*;

public class OrderBook {
    private PriorityQueue<Order> buyOrders;
    private PriorityQueue<Order> sellOrders;
    private List<Trade> trades;

    public OrderBook() {
        buyOrders = new PriorityQueue<>((o1, o2) -> Integer.compare(o2.getPrice(), o1.getPrice()));

        sellOrders = new PriorityQueue<>(Comparator.comparingInt(Order::getPrice));

        trades = new ArrayList<>();
    }

    public void addOrder(String orderId, char side, int price, int quantity) {
        if (side == 'B') {
            matchBuyOrder(orderId, side, price, quantity);
        } else if (side == 'S') {
            matchSellOrder(orderId, side, price, quantity);
        }
    }

    private void matchBuyOrder(String orderId, char side, int price, int quantity) {
        int leftBuyOrderQuantity = quantity;
        while (!sellOrders.isEmpty() && leftBuyOrderQuantity > 0) {
            Order sellOrder = sellOrders.peek();
            if (sellOrder.getPrice() > price) {
                break;
            }

            int tradeQuantity = Math.min(leftBuyOrderQuantity, sellOrder.getQuantity());
            leftBuyOrderQuantity = Math.max(leftBuyOrderQuantity-tradeQuantity,0);
            int tradePrice = sellOrder.getPrice();

            trades.add(new Trade(orderId, sellOrder.getOrderId(), tradePrice, tradeQuantity));

            Order newSellOrder = new Order( sellOrder.getOrderId(), sellOrder.getSide(), sellOrder.getPrice(),
                    sellOrder.getQuantity() - tradeQuantity);
            sellOrders.poll();

            if (newSellOrder.getQuantity() > 0) {
                sellOrders.add(newSellOrder);
            }
        }

        if (leftBuyOrderQuantity > 0) {
            Order newBuyOrder = new Order(orderId, side, price,
                                            leftBuyOrderQuantity);
            buyOrders.add(newBuyOrder);
        }
    }

    private void matchSellOrder(String orderId, char side, int price, int quantity) {
        int leftSellOrderQuantity = quantity;
        while (!buyOrders.isEmpty() && leftSellOrderQuantity > 0) {
            Order buyOrder = buyOrders.peek();
            if (buyOrder.getPrice() < price) {
                break;
            }

            int tradeQuantity = Math.min(leftSellOrderQuantity, buyOrder.getQuantity());
            leftSellOrderQuantity = Math.max(leftSellOrderQuantity-tradeQuantity,0);
            int tradePrice = buyOrder.getPrice();

            trades.add(new Trade(orderId, buyOrder.getOrderId(), tradePrice, tradeQuantity));

            Order newBuyOrder = new Order( buyOrder.getOrderId(), buyOrder.getSide(), buyOrder.getPrice(),
                    buyOrder.getQuantity() - tradeQuantity);
            buyOrders.poll();

            if (buyOrder.getQuantity() > 0) {
                buyOrders.add(newBuyOrder);
            }
        }

        if (leftSellOrderQuantity > 0) {
            Order newSellOrder = new Order(orderId, side, price,
                                            leftSellOrderQuantity);
            sellOrders.add(newSellOrder);
        }
    }

    public void printTrades() {
        for (Trade trade : trades) {
            System.out.println(trade);
        }
    }

    public void printOrderBook() {
        List<Order> sortedBuyOrders = new ArrayList<>(buyOrders);
        sortedBuyOrders.sort((o1, o2) -> Integer.compare(o2.getPrice(), o1.getPrice()));

        List<Order> sortedSellOrders = new ArrayList<>(sellOrders);
        sortedSellOrders.sort(Comparator.comparingInt(Order::getPrice));

        int maxSize = Math.max(sortedBuyOrders.size(), sortedSellOrders.size());

        for (int i = 0; i < maxSize; i++) {
            if (i < sortedBuyOrders.size()) {
                Order buyOrder = sortedBuyOrders.get(i);
                System.out.printf("%,10d %6d |", buyOrder.getQuantity(), buyOrder.getPrice());
            } else {
                System.out.print("                  |");
            }

            if (i < sortedSellOrders.size()) {
                Order sellOrder = sortedSellOrders.get(i);
                System.out.printf(" %6d %,10d%n", sellOrder.getPrice(), sellOrder.getQuantity());
            } else {
                System.out.println();
            }
        }
    }
}
