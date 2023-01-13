package com.example.snakegame01;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.util.Objects;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("LoadingScreen.fxml"));
            //Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("LoadingScreen.fxml")));
            Scene scene = new Scene(root);

            stage.setTitle("Snake");
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.setResizable(false);
            stage.show();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}