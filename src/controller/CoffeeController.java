package src.controller;

import src.model.Coffee;
import src.model.MenuItem;
import src.model.Order;
import src.model.OrdersManager;
import src.enums.CoffeeAddIn;
import src.enums.CupSize;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

/**
 * Controller for the coffee ordering view.
 */
public class CoffeeController implements OrdersAware {

    @FXML
    private ComboBox<CupSize> sizeCombo;

    @FXML
    private ListView<CoffeeAddIn> addInsList;

    @FXML
    private Spinner<Integer> quantitySpinner;

    @FXML
    private Label priceLabel;

    @FXML
    private ImageView coffeeImage;

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
        sizeCombo.setItems(FXCollections.observableArrayList(CupSize.values()));
        sizeCombo.getSelectionModel().selectedItemProperty()
                .addListener((obs, o, n) -> updatePrice());

        addInsList.setItems(FXCollections.observableArrayList(CoffeeAddIn.values()));
        addInsList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        addInsList.getSelectionModel().selectedItemProperty()
                .addListener((obs, o, n) -> updatePrice());

        quantitySpinner.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 1));
        quantitySpinner.valueProperty().addListener((obs, o, n) -> updatePrice());

        coffeeImage.setImage(new Image("/images/coffee-large.png"));

        sizeCombo.getSelectionModel().select(CupSize.SHORT);
    }

    private void updatePrice() {
        CupSize size = sizeCombo.getValue();
        Integer qty = quantitySpinner.getValue();
        if (size == null || qty == null) {
            priceLabel.setText("$0.00");
            return;
        }
        var selectedAddIns = new ArrayList<>(addInsList.getSelectionModel().getSelectedItems());
        MenuItem item = new Coffee(size, qty, selectedAddIns);
        priceLabel.setText(String.format("$%.2f", item.price()));
    }

    @FXML
    private void addCoffeeToOrder() {
        CupSize size = sizeCombo.getValue();
        Integer qty = quantitySpinner.getValue();
        if (size == null || qty == null || qty <= 0) {
            show("Please choose a size and quantity.");
            return;
        }
        var selectedAddIns = new ArrayList<>(addInsList.getSelectionModel().getSelectedItems());
        Order order = ensureCurrentOrder();
        order.addItem(new Coffee(size, qty, selectedAddIns));
        show("Coffee added to order #" + order.getOrderNumber());
    }

    private Order ensureCurrentOrder() {
        Order cur = ordersManager.getCurrentOrder();
        if (cur == null) cur = ordersManager.startNewOrder();
        return cur;
    }

    private void show(String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }
}
