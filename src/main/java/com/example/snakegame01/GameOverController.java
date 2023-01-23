package com.example.snakegame01;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

public class GameOverController {
    private Stage stage;
    private Scene scene;

    public void switchToMenu(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Menu.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);

        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
        MenuController.setMulti(false);
        MenuController.setColor(false);
        MenuController.setBackground(false);
        MenuController.setWalls(false);
        MenuController.setObstacles(false);
        MenuController.setBombs(false);
        App.setToStartValues();
        //Sound.playSound();
    }

    public void exitGame(ActionEvent Event) throws IOException {
        Alert a = new Alert(Alert.AlertType.NONE);
        a.setAlertType(Alert.AlertType.CONFIRMATION);
        a.setContentText("Do you want to close the game?");
        a.showAndWait();
        System.exit(0);
    }

    public void switchToGame(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Game.fxml")));
        //stage = (Stage) start.getScene().getWindow();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Snake");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
        App.setToStartValues();

        //launch Game:
        App Snake = new App();
        Snake.start(stage);
    }
}

