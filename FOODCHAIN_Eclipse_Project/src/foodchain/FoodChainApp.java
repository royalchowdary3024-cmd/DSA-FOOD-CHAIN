package foodchain;

import foodchain.model.*;
import foodchain.service.FoodChainService;
import foodchain.dsa.StringAlgorithms;
import foodchain.dsa.NetworkAlgorithms;
import java.util.*;

public class FoodChainApp {
    private static final Scanner sc = new Scanner(System.in);
    private static final FoodChainService service = new FoodChainService();

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("       FOODCHAIN MANAGEMENT SYSTEM");
        System.out.println("========================================");
        boolean running = true;
        while (running) {
            showMenu();
            int choice = readInt("Choose an option: ");
            try {
                switch (choice) {
                    case 1: listProducts(); break;
                    case 2: addProduct(); break;
                    case 3: searchProducts(); break;
                    case 4: restock(); break;
                    case 5: listCustomers(); break;
                    case 6: addCustomer(); break;
                    case 7: placeOrder(); break;
                    case 8: listOrders(); break;
                    case 9: runStringAlgorithms(); break;
                    case 10: runNetworkDemo(); break;
                    case 0: running = false; System.out.println("Thank you for using FOODCHAIN."); break;
                    default: System.out.println("Please choose a valid menu option.");
                }
            } catch (RuntimeException ex) {
                System.out.println("Could not complete operation: " + ex.getMessage());
            }
            System.out.println();
        }
        sc.close();
    }

    private static void showMenu() {
        System.out.println("\\n--------------- MAIN MENU ---------------");
        System.out.println("1. View products and inventory");
        System.out.println("2. Add product");
        System.out.println("3. Search product (KMP + typo-tolerant matching)");
        System.out.println("4. Restock product");
        System.out.println("5. View customers");
        System.out.println("6. Add customer");
        System.out.println("7. Place order");
        System.out.println("8. View orders");
        System.out.println("9. Demonstrate string / DP algorithms");
        System.out.println("10. Demonstrate distribution network algorithms");
        System.out.println("0. Exit");
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = sc.nextLine().trim();
            try { return Integer.parseInt(s); }
            catch (NumberFormatException e) { System.out.println("Enter a whole number."); }
        }
    }

    private static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = sc.nextLine().trim();
            try {
                double v = Double.parseDouble(s);
                if (v >= 0) return v;
            } catch (NumberFormatException ignored) {}
            System.out.println("Enter a valid non-negative number.");
        }
    }

    private static String readText(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = sc.nextLine().trim();
            if (!s.isEmpty()) return s;
            System.out.println("This field cannot be empty.");
        }
    }

    private static void listProducts() {
        System.out.println("\\n--- PRODUCTS & INVENTORY ---");
        for (Product p : service.getProducts()) System.out.println(p);
    }

    private static void addProduct() {
        String name = readText("Product name: ");
        String category = readText("Category: ");
        double price = readDouble("Price (Rs.): ");
        int stock = readInt("Opening stock: ");
        if (stock < 0) { System.out.println("Stock cannot be negative."); return; }
        System.out.println("Added: " + service.addProduct(name, category, price, stock));
    }

    private static void searchProducts() {
        String q = readText("Enter product name or part of name: ");
        List<Product> found = service.searchProducts(q);
        if (found.isEmpty()) System.out.println("No matching products found.");
        else {
            System.out.println("Matching products:");
            for (Product p : found) System.out.println(p);
        }
    }

    private static void restock() {
        listProducts();
        int id = readInt("Product ID to restock: ");
        int qty = readInt("Quantity to add: ");
        System.out.println(service.restock(id, qty) ? "Inventory updated." : "Invalid product ID or quantity.");
    }

    private static void listCustomers() {
        System.out.println("\\n--- CUSTOMERS ---");
        for (Customer c : service.getCustomers()) System.out.println(c);
    }

    private static void addCustomer() {
        String name = readText("Customer name: ");
        String phone = readText("Phone: ");
        System.out.println("Added: " + service.addCustomer(name, phone));
    }

    private static void placeOrder() {
        listCustomers();
        int cid = readInt("Customer ID: ");
        listProducts();
        int pid = readInt("Product ID: ");
        int qty = readInt("Quantity: ");
        System.out.println(service.placeOrder(cid, pid, qty));
    }

    private static void listOrders() {
        System.out.println("\\n--- ORDERS ---");
        if (service.getOrders().isEmpty()) System.out.println("No orders placed yet.");
        for (Order o : service.getOrders()) System.out.println(o);
    }

    private static void runStringAlgorithms() {
        System.out.println("\\n--- STRING / DYNAMIC PROGRAMMING DEMO ---");
        String text = readText("Text / product description: ").toLowerCase(Locale.ROOT);
        String pattern = readText("Pattern to search: ").toLowerCase(Locale.ROOT);
        System.out.println("KMP match positions: " + StringAlgorithms.kmpSearch(text, pattern));
        System.out.println("Rabin-Karp match positions: " + StringAlgorithms.rabinKarp(text, pattern));
        System.out.println("Z-function values: " + Arrays.toString(StringAlgorithms.zFunction(pattern + "$" + text)));
        String target = readText("Compare with another product name (for edit distance): ").toLowerCase(Locale.ROOT);
        System.out.println("Levenshtein distance: " + StringAlgorithms.levenshtein(pattern, target));
        System.out.println("Damerau-Levenshtein distance: " + StringAlgorithms.damerauLevenshtein(pattern, target));
        int[] sa = StringAlgorithms.suffixArray(text);
        System.out.println("Suffix array: " + Arrays.toString(sa));
        System.out.println("LCP array: " + Arrays.toString(StringAlgorithms.lcpArray(text, sa)));
    }

    private static void runNetworkDemo() {
        System.out.println("\\n--- DISTRIBUTION NETWORK DEMO ---");
        // Nodes: 0=Supplier, 1=Warehouse A, 2=Warehouse B, 3=Market
        int[][] capacity = {
            {0, 10, 8, 0},
            {0, 0, 2, 5},
            {0, 0, 0, 10},
            {0, 0, 0, 0}
        };
        int maxFlow = NetworkAlgorithms.edmondsKarp(capacity, 0, 3);
        System.out.println("Nodes: Supplier -> Warehouse A / Warehouse B -> Market");
        System.out.println("Maximum distribution flow: " + maxFlow + " units");
        boolean[][] assignment = {
            {true, true, false},
            {true, false, true},
            {false, true, true}
        };
        System.out.println("Maximum supplier-to-warehouse assignment: "
                + NetworkAlgorithms.maximumBipartiteMatching(assignment));
    }
}
