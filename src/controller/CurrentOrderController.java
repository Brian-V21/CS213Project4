package src.controller;

import src.model.MenuItem;
import src.model.Order;
import src.model.OrdersManager;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

/**
 * Shows the current order, allows removing items and placing order.
 */
public class CurrentOrderController implements OrdersAware {

    @FXML
    private ListView<MenuItem> itemsList;

    @FXML
    private Label subtotalLabel, taxLabel, totalLabel, orderNumberLabel;

    @FXML
    private Button removeItemBtn, clearBtn, placeOrderBtn;

    private OrdersManager ordersManager;

    @Override
    public void setOrdersManager(OrdersManager manager) {
        this.ordersManager = manager;
        refresh();
    }

    @FXML
    private void initialize() {
        itemsList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    @FXML
    private void removeSelectedItem() {
        Order order = ordersManager.getCurrentOrder();
        if (order == null) return;

        MenuItem selected = itemsList.getSelectionModel().getSelectedItem();
        if (selected != null) {
            order.removeItem(selected);
            refresh();
        }
    }

    @FXML
    private void clearCurrentOrder() {
        Order order = ordersManager.getCurrentOrder();
        if (order != null) {
            order.clear();
            refresh();
        }
    }

    @FXML
    private void placeOrder() {
        if (ordersManager.placeCurrentOrder()) {
            show("Order placed!");
            ordersManager.startNewOrder();
            refresh();
        } else {
            show("No items in current order.");
        }
    }

    private void refresh() {
        Order order = ordersManager.getCurrentOrder();
        if (order == null) {
            itemsList.setItems(FXCollections.emptyObservableList());
            subtotalLabel.setText("$0.00");
            taxLabel.setText("$0.00");
            totalLabel.setText("$0.00");
            orderNumberLabel.setText("No current order");
            return;
        }
        itemsList.setItems(FXCollections.observableArrayList(order.getItems()));
        subtotalLabel.setText(String.format("$%.2f", order.getSubTotal()));
        taxLabel.setText(String.format("$%.2f", order.getTax()));
        totalLabel.setText(String.format("$%.2f", order.getTotal()));
        orderNumberLabel.setText("Order #" + order.getOrderNumber());
    }

    private void show(String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }
}
