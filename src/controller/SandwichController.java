package src.controller;

import src.model.Order;
import src.model.OrdersManager;
import src.model.Sandwich;
import src.enums.Bread;
import src.enums.Protein;
import src.enums.SandwichAddOn;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

/**
 * Controller for the sandwich ordering view.
 */
public class SandwichController implements OrdersAware {

    @FXML
    private ComboBox<Bread> breadCombo;

    @FXML
    private ComboBox<Protein> proteinCombo;

    @FXML
    private ListView<SandwichAddOn> addOnList;

    @FXML
    private Spinner<Integer> quantitySpinner;

    @FXML
    private Label priceLabel;

    @FXML
    private ImageView sandwichImage;

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
        breadCombo.setItems(FXCollections.observableArrayList(Bread.values()));
        proteinCombo.setItems(FXCollections.observableArrayList(Protein.values()));
        addOnList.setItems(FXCollections.observableArrayList(SandwichAddOn.values()));
        addOnList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        quantitySpinner.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 1));

        breadCombo.getSelectionModel().selectedItemProperty()
                .addListener((o, a, b) -> updatePrice());
        proteinCombo.getSelectionModel().selectedItemProperty()
                .addListener((o, a, b) -> updatePrice());
        addOnList.getSelectionModel().selectedItemProperty()
                .addListener((o, a, b) -> updatePrice());
        quantitySpinner.valueProperty()
                .addListener((o, a, b) -> updatePrice());

        sandwichImage.setImage(new Image("/images/sandwich-large.png"));

        breadCombo.getSelectionModel().select(Bread.BAGEL);
        proteinCombo.getSelectionModel().select(Protein.CHICKEN);
    }

    private void updatePrice() {
        Bread bread = breadCombo.getValue();
        Protein protein = proteinCombo.getValue();
        Integer qty = quantitySpinner.getValue();
        if (bread == null || protein == null || qty == null) {
            priceLabel.setText("$0.00");
            return;
        }
        var addOns = new ArrayList<>(addOnList.getSelectionModel().getSelectedItems());
        Sandwich s = new Sandwich(bread, protein, qty, addOns);
        priceLabel.setText(String.format("$%.2f", s.price()));
    }

    @FXML
    private void addSandwichToOrder() {
        Bread bread = breadCombo.getValue();
        Protein protein = proteinCombo.getValue();
        Integer qty = quantitySpinner.getValue();
        if (bread == null || protein == null || qty == null || qty <= 0) {
            show("Please choose bread, protein, and quantity.");
            return;
        }
        var addOns = new ArrayList<>(addOnList.getSelectionModel().getSelectedItems());
        Order order = ensureCurrentOrder();
        order.addItem(new Sandwich(bread, protein, qty, addOns));
        show("Sandwich added to order #" + order.getOrderNumber());
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
