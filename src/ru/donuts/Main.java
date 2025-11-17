

import src.model.OrdersManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        OrdersManager manager = new OrdersManager();

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/edu/ru/donuts/view/main-view.fxml"));
        Scene scene = new Scene(loader.load());

        src.controller.MainController controller = loader.getController();
        controller.setOrdersManager(manager);

        stage.setTitle("RU Donuts - Main Menu");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
