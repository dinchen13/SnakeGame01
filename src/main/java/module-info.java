module com.example.snakegame01 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.snakegame01 to javafx.fxml;
    exports com.example.snakegame01;
}