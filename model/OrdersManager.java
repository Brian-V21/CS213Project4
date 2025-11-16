package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.PrintWriter;

public class OrdersManager {
    private int nextOrder = 10001;
    private final ObersableList<Order> placed = FXCollections.observableArrayList();
    private Order current = new Order(nextOrder++);

    public Order currentOrder() {
        return current;
    }

    public ObservableList<Order> placeOrders() {
        return placed;
    }

    public void placeCurrent() {
        if (current.getItems().isEmpty())
            return;
        placed.add(current);
        current = new Order(nextOrder++);
    }

    public void cancelOrder(Order o) {
        placed.remove(o);
    }

    public void exportTo(File file) throws Exception {
        try (PrintWriter pw = new PrintWriter(file, "UTF-8")) {
            for (Order o : placed) {
                pw.println(o.recieptText());
                pw.println("----");
            }
        }
    }
}
