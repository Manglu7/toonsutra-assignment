package com.example.toonsutra;

import com.example.toonsutra.models.Order;
import com.example.toonsutra.services.OrderBook;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class exchange {

    public static void main(String[] args) {
        OrderBook orderBook = new OrderBook();
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) {
                break;
            }

            String[] parts = line.split(",");

            if (parts.length != 4) {
                System.err.println("Invalid input format: " + line);
                continue;
            }

            String orderId = parts[0].trim();
            if (orderId.isEmpty()) {
                System.err.println("Order ID is missing: " + line);
                continue;
            }

            char side;
            if (parts[1] == null || parts[1].trim().isEmpty()) {
                System.err.println("Side is missing: " + line);
                continue;
            } else {
                side = parts[1].trim().charAt(0);
            }

            int price;
            try {
                price = Integer.parseInt(parts[2].trim());
            } catch (NumberFormatException e) {
                System.err.println("Invalid price format: " + line);
                continue;
            }

            int quantity;
            try {
                quantity = Integer.parseInt(parts[3].trim());
            } catch (NumberFormatException e) {
                System.err.println("Invalid quantity format: " + line);
                continue;
            }

            orderBook.addOrder(orderId, side, price, quantity);
        }

        orderBook.printTrades();

        orderBook.printOrderBook();
        SpringApplication.run(exchange.class, args);
    }

}
