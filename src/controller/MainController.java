package src.controller;

import src.model.OrdersManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;


public class MainController {

    @FXML
    private Button donutsBtn, coffeeBtn, sandwichBtn, currentOrderBtn, allOrdersBtn;

    private OrdersManager ordersManager;

    public void setOrdersManager(OrdersManager ordersManager) {
        this.ordersManager = ordersManager;
    }

    @FXML
    private void openDonutView() throws IOException {
        openModal("/edu/ru/donuts/view/donut-view.fxml",
                "Order Donuts", DonutController.class);
    }

    @FXML
    private void openCoffeeView() throws IOException {
        openModal("/edu/ru/donuts/view/coffee-view.fxml",
                "Order Coffee", CoffeeController.class);
    }

    @FXML
    private void openSandwichView() throws IOException {
        openModal("/edu/ru/donuts/view/sandwich-view.fxml",
                "Order Sandwich", SandwichController.class);
    }

    @FXML
    private void openCurrentOrderView() throws IOException {
        openModal("/edu/ru/donuts/view/current-order-view.fxml",
                "Current Order", CurrentOrderController.class);
    }

    @FXML
    private void openAllOrdersView() throws IOException {
        openModal("/edu/ru/donuts/view/all-orders-view.fxml",
                "All Orders", AllOrdersController.class);
    }


    private <T> void openModal(String fxmlPath, String title, Class<T> controllerType)
            throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Scene scene = new Scene(loader.load());
        Object controller = loader.getController();

        if (controllerType.isInstance(controller) && controller instanceof OrdersAware) {
            ((OrdersAware) controller).setOrdersManager(ordersManager);
        }

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }
}
