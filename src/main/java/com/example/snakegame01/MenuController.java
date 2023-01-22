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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

import static com.example.snakegame01.Sound.playSound;

public class MenuController {

    @FXML
    public Button start;
    @FXML
    public Button howtoplay;
    @FXML
    public Button play;
    @FXML
    public Slider playerCount;
    private static boolean multiplayer=false;
    @FXML
    public Slider snakeColor;
    private static boolean singlecolor=false;
    @FXML
    public Slider backgroundColor;
    private static boolean darkmode=false;
    @FXML
    public Slider walls;
    private static boolean wallsActivated=false;
    @FXML
    public Slider bombs;
    @FXML
    public Slider obstacles;
    private static boolean obstaclesActivated=false;


    private Stage stage;
    private Scene scene;
    private Parent root;

    public static boolean isMultiplayer (){
        return multiplayer;
    }
    public static void setMulti(boolean value){multiplayer=value;}
    public static boolean isSinglecolor(){return singlecolor;}
    public static void setColor(boolean value){singlecolor=value;}
    public static boolean isDarkmode(){return darkmode;}
    public static void setBackground(boolean value){darkmode=value;}
    public static boolean isWallsActivated(){return wallsActivated;}
    public static void setWalls(boolean value){wallsActivated=value;}
    public static boolean isObstaclesActivated() {return obstaclesActivated;}
    public static void setObstacles(boolean value) {
        obstaclesActivated=value;
    }

    public void switchToMenu(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("menu.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);

        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
        //Sound.playSound();
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

        //launch Game:
        App Snake = new App();
        Snake.start(stage);
    }
    public void switchToInfo(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("howtoplay.fxml")));
        //stage = (Stage) howtoplay.getScene().getWindow();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("How to play");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();

    }
    public void exitGame(ActionEvent Event) throws IOException {

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
        if(snakeColor.getValue()>0.5){
            singlecolor=true;
        }
        if(backgroundColor.getValue()>0.5){
            darkmode=true;
        }
        if(walls.getValue()>0.5){
            wallsActivated=true;
        }
        if(obstacles.getValue()>0.5){
            obstaclesActivated=true;
        }


    }


}



