package src.controller;

import src.model.MenuItem;
import src.model.Order;
import src.model.OrdersManager;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Shows placed orders, allows cancellation and export.
 */
public class AllOrdersController implements OrdersAware {

    @FXML
    private ListView<Order> ordersList;

    @FXML
    private ListView<MenuItem> itemsList;

    @FXML
    private Label totalLabel;

    private OrdersManager ordersManager;

    @Override
    public void setOrdersManager(OrdersManager manager) {
        this.ordersManager = manager;
        refreshOrders();
    }

    @FXML
    private void initialize() {
        ordersList.getSelectionModel().selectedItemProperty()
                .addListener((obs, o, n) -> showOrderDetails(n));
    }

    private void refreshOrders() {
        ordersList.setItems(FXCollections.observableArrayList(
                ordersManager.getPlacedOrders()));
        itemsList.setItems(FXCollections.emptyObservableList());
        totalLabel.setText("$0.00");
    }

    private void showOrderDetails(Order order) {
        if (order == null) {
            itemsList.setItems(FXCollections.emptyObservableList());
            totalLabel.setText("$0.00");
            return;
        }
        itemsList.setItems(FXCollections.observableArrayList(order.getItems()));
        totalLabel.setText(String.format("$%.2f", order.getTotal()));
    }

    @FXML
    private void cancelSelectedOrder() {
        Order order = ordersList.getSelectionModel().getSelectedItem();
        if (order == null) return;
        ordersManager.cancelPlacedOrder(order);
        refreshOrders();
    }

    @FXML
    private void exportOrders() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Export Orders");
        chooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Text Files", "*.txt")
        );
        Stage stage = (Stage) ordersList.getScene().getWindow();
        var file = chooser.showSaveDialog(stage);
        if (file == null) return;

        try {
            ordersManager.exportOrders(Path.of(file.toURI()));
            show("Orders exported to " + file.getName());
        } catch (IOException e) {
            show("Failed to export: " + e.getMessage());
        }
    }

    private void show(String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }
}
