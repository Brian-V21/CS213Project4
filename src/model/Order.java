package src.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Order {
    private final int orderNumber;
    private final List<MenuItem> items = new ArrayList<>();

    public Order(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public List<MenuItem> getItems() {
        return items;
    }

    public void addItem(MenuItem mi) {
        items.add(mi);
    } 

    public void removeItem(MenuItem mi) {
        items.remove(mi);
    }

    public void clear() {
        items.clear();
    }

    public double subtotal() {
        return items.stream().mapToDouble(MenuItem::price).sum();
    }

    public double tax() {
        return subtotal() * Util.NJ_TAX;
    }

    public double total() {
        return subtotal() + tax();
    }

    public String recieptText() {
        String lines = items.stream()
            .map(MenuItem::displayLine)
            .collect(Collectors.joining("\n"));
        return "Order #" + orderNumber + "\n" + lines + "\n" +
                "Subtotal: " + Util.fmt(subtotal()) + "\n" +
                "Tax: " + Util.fmt(tax()) + "\n" +
                "Total: " + Util.fmt(total()) + "\n";
    }
}
