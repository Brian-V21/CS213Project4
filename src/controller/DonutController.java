package edu.ru.donuts.controller;

import src.model.Donut;
import src.model.MenuItem;
import src.model.Order;
import src.model.OrdersManager;
import src.enums.DonutFlavor;
import src.enums.DonutType;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.stream.Collectors;

/**
 * Controller for the donut ordering view.
 */
public class DonutController implements OrdersAware {

    @FXML
    private ComboBox<DonutType> typeCombo;

    @FXML
    private ComboBox<DonutFlavor> flavorCombo;

    @FXML
    private Spinner<Integer> quantitySpinner;

    @FXML
    private Label runningPriceLabel;

    @FXML
    private ImageView donutImage;

    @FXML
    private Button addButton;

    private OrdersManager ordersManager;

    @Override
    public void setOrdersManager(OrdersManager manager) {
        this.ordersManager = manager;
        if (ordersManager.getCurrentOrder() == null) {
            ordersManager.startNewOrder();
        }
    }

    @FXML
    private void initialize() {
        typeCombo.setItems(FXCollections.observableArrayList(DonutType.values()));
        typeCombo.getSelectionModel().selectedItemProperty()
                .addListener((obs, oldVal, newVal) -> updateFlavorList(newVal));

        quantitySpinner.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 12, 1));
        quantitySpinner.valueProperty()
                .addListener((obs, oldVal, newVal) -> updatePrice());

        flavorCombo.getSelectionModel().selectedItemProperty()
                .addListener((obs, oldVal, newVal) -> updatePrice());

        typeCombo.getSelectionModel().select(DonutType.YEAST);
    }

    private void updateFlavorList(DonutType type) {
        if (type == null) {
            flavorCombo.setItems(FXCollections.emptyObservableList());
            donutImage.setImage(null);
            return;
        }

        // filter flavors by type
        var flavors = FXCollections.observableArrayList(
                FXCollections.observableArrayList(DonutFlavor.values())
                        .stream()
                        .filter(f -> f.getType() == type)
                        .collect(Collectors.toList())
        );
        flavorCombo.setItems(flavors);
        if (!flavors.isEmpty()) {
            flavorCombo.getSelectionModel().select(0);
        }

        // change image per type (adjust paths to resources)
        String imgPath;
        switch (type) {
            case YEAST -> imgPath = "/images/yeast.png";
            case CAKE -> imgPath = "/images/cake.png";
            case HOLE -> imgPath = "/images/holes.png";
            case SEASONAL -> imgPath = "/images/seasonal.png";
            default -> imgPath = null;
        }
        if (imgPath != null) {
            donutImage.setImage(new Image(imgPath));
        }
        updatePrice();
    }

    private void updatePrice() {
        DonutFlavor flavor = flavorCombo.getValue();
        Integer qty = quantitySpinner.getValue();
        if (flavor == null || qty == null) {
            runningPriceLabel.setText("$0.00");
            return;
        }
        MenuItem item = new Donut(flavor, qty);
        runningPriceLabel.setText(String.format("$%.2f", item.price()));
    }

    @FXML
    private void addDonutToOrder() {
        DonutFlavor flavor = flavorCombo.getValue();
        Integer qty = quantitySpinner.getValue();
        if (flavor == null || qty == null || qty <= 0) {
            showAlert("Please select a flavor and quantity.");
            return;
        }

        Order order = ensureCurrentOrder();
        order.addItem(new Donut(flavor, qty));
        showAlert("Donut(s) added to order #" + order.getOrderNumber());
    }

    private Order ensureCurrentOrder() {
        Order cur = ordersManager.getCurrentOrder();
        if (cur == null) {
            cur = ordersManager.startNewOrder();
        }
        return cur;
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
