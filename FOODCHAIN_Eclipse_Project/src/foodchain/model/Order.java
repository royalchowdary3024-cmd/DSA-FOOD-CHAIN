package foodchain.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Order {
    private final int id;
    private final int customerId;
    private final int productId;
    private final int quantity;
    private final double total;
    private final String createdAt;

    public Order(int id, int customerId, int productId, int quantity, double total) {
        this.id = id;
        this.customerId = customerId;
        this.productId = productId;
        this.quantity = quantity;
        this.total = total;
        this.createdAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
    }

    @Override
    public String toString() {
        return "Order ID: " + id + " | Customer ID: " + customerId +
                " | Product ID: " + productId + " | Quantity: " + quantity +
                " | Total: Rs. " + String.format("%.2f", total) + " | " + createdAt;
    }
}
