package foodchain.service;

import foodchain.model.*;
import foodchain.dsa.StringAlgorithms;
import java.util.*;

public class FoodChainService {
    private final Map<Integer, Product> products = new LinkedHashMap<>();
    private final Map<Integer, Customer> customers = new LinkedHashMap<>();
    private final List<Order> orders = new ArrayList<>();
    private int nextProductId = 1, nextCustomerId = 1, nextOrderId = 1;

    public FoodChainService() {
        addProduct("Basmati Rice", "Grains", 95.0, 50);
        addProduct("Tomato", "Vegetables", 30.0, 100);
        addProduct("Potato", "Vegetables", 28.0, 80);
        addProduct("Green Chilli", "Vegetables", 45.0, 35);
        addProduct("Toor Dal", "Pulses", 140.0, 40);
        addCustomer("Demo Customer", "9876543210");
    }

    public Product addProduct(String name, String category, double price, int stock) {
        Product p = new Product(nextProductId++, name, category, price, stock);
        products.put(p.getId(), p);
        return p;
    }

    public Collection<Product> getProducts() { return Collections.unmodifiableCollection(products.values()); }
    public Collection<Customer> getCustomers() { return Collections.unmodifiableCollection(customers.values()); }
    public List<Order> getOrders() { return Collections.unmodifiableList(orders); }
    public Product getProduct(int id) { return products.get(id); }
    public Customer getCustomer(int id) { return customers.get(id); }

    public Customer addCustomer(String name, String phone) {
        Customer c = new Customer(nextCustomerId++, name, phone);
        customers.put(c.getId(), c);
        return c;
    }

    public boolean restock(int productId, int quantity) {
        Product p = products.get(productId);
        if (p == null || quantity <= 0) return false;
        p.setStock(p.getStock() + quantity);
        return true;
    }

    public String placeOrder(int customerId, int productId, int quantity) {
        Customer customer = customers.get(customerId);
        Product product = products.get(productId);
        if (customer == null) return "Customer ID not found.";
        if (product == null) return "Product ID not found.";
        if (quantity <= 0) return "Quantity must be greater than zero.";
        if (product.getStock() < quantity) return "Insufficient stock. Available: " + product.getStock();
        product.setStock(product.getStock() - quantity);
        double total = product.getPrice() * quantity;
        orders.add(new Order(nextOrderId++, customerId, productId, quantity, total));
        return String.format("Order placed successfully for %s. Total: Rs. %.2f", product.getName(), total);
    }

    public List<Product> searchProducts(String query) {
        List<Product> matches = new ArrayList<>();
        String q = query.toLowerCase(Locale.ROOT).trim();
        for (Product p : products.values()) {
            String name = p.getName().toLowerCase(Locale.ROOT);
            if (StringAlgorithms.kmpSearch(name, q).size() > 0
                    || StringAlgorithms.levenshtein(name, q) <= 2) matches.add(p);
        }
        return matches;
    }
}
