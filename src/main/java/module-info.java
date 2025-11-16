module org.example.project4softwaremetho {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.project4softwaremetho to javafx.fxml;
    exports org.example.project4softwaremetho;
}