package com.example.snakegame01;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

import static javafx.application.Application.launch;
public class MenuController {

    @FXML
    public Button start;
    @FXML
    public Button howtoplay;
    @FXML
    public Button exit;
    @FXML
    public Button play;
    @FXML
    public Slider playerCount;
    public static boolean multiplayer=false;
    @FXML
    public Slider snakeColor;
    @FXML
    public Slider backgroundColor;
    @FXML
    public Slider bombs;
    @FXML
    public Slider obstacles;
    @FXML
    public Slider walls;

    private Stage stage;
    private Scene scene;
    private Parent root;

    public static boolean getMulti (){
        return multiplayer;
    }

    public void switchToLoading1(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("menu.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);

        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
        launch();
    }

    public void switchToScene2(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Game.fxml")));
        //stage = (Stage) start.getScene().getWindow();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Snake");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();

        //launch Game:
        App Snake = new App();
        Snake.start(stage);
    }
    public void switchToScene3(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("howtoplay.fxml")));
        //stage = (Stage) howtoplay.getScene().getWindow();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("How to play");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
        //launch();
    }
    public void exitgame(ActionEvent Event) throws IOException {

        Alert a = new Alert(Alert.AlertType.NONE);
        a.setAlertType(Alert.AlertType.CONFIRMATION);
        a.setContentText("Do you want to close the game?");
        a.showAndWait();
        System.exit(0);
    }

    public void onSliderChanged(){
        if(playerCount.getValue()>0.5){
            multiplayer=true;
        }

    }


}



