package com.example.snakegame01;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

import static javafx.application.Application.launch;

public class MenuController {


    @FXML
    public Button start;
    public Button howtoplay;
    private Stage stage;
    private Scene scene;
    private Parent root;



    public void switchToScene2(ActionEvent event) throws IOException {
       Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Game.fxml")));
        stage = (Stage)start.getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Snake");
        stage.setScene(scene);
        stage.show();
        //launch();
        App Snake = new App();
        Snake.start(stage);

    }


    public void switchToScene3(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("howtoplay.fxml")));
        stage = (Stage)howtoplay.getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("How to play");
        stage.setScene(scene);
        stage.show();
        launch();
    }

    public void exitgame(ActionEvent Event) throws IOException {

        Alert a = new Alert(Alert.AlertType.NONE);
        a.setAlertType(Alert.AlertType.CONFIRMATION);
        a.setContentText("Do you want to close the game?");
        a.showAndWait();
        System.exit(0);
    }

}



