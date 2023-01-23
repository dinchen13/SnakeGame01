package com.example.snakegame01;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

import static com.example.snakegame01.Sound.playSound;

public class MenuController {
    @FXML
    public Button start;
    @FXML
    public Button howToPlay;
    @FXML
    public Button play;
    @FXML
    public ToggleButton buttonSinglePlayer;
    @FXML
    public ToggleButton buttonMultiPlayer;
    @FXML
    public ToggleButton buttonOneColor;
    @FXML
    public ToggleButton buttonMulticolor;
    @FXML
    public ToggleButton buttonLightMode;
    @FXML
    public ToggleButton buttonDarkMode;
    @FXML
    public ToggleButton buttonWithoutWalls;
    @FXML
    public ToggleButton buttonWithWalls;
    @FXML
    public ToggleButton buttonWithoutBombs;
    @FXML
    public ToggleButton buttonWithBombs;
    @FXML
    public ToggleButton buttonWithoutObstacles;
    @FXML
    public ToggleButton buttonWithObstacles;

    private static boolean multiplayer = false;
    private static boolean singleColor = false;
    private static boolean darkMode = false;
    private static boolean wallsActivated = false;
    private static boolean bombsActivated = false;
    private static boolean obstaclesActivated = false;
    private Stage stage;
    private Scene scene;

    //++++++++++++++++++++++++++++++++++++ getter & setters +++++++++++++++++++++++++++++++++++++++++
    public static boolean isMultiplayer() {
        return multiplayer;
    }
    public static void setMulti(boolean value) {
        multiplayer = value;
    }
    public static boolean isSingleColor() {
        return singleColor;
    }
    public static void setColor(boolean value) {
        singleColor = value;
    }
    public static boolean isDarkMode() {
        return darkMode;
    }
    public static void setBackground(boolean value) {
        darkMode = value;
    }
    public static boolean isWallsActivated() {
        return wallsActivated;
    }
    public static void setWalls(boolean value) {
        wallsActivated = value;
    }
    public static boolean isObstaclesActivated() {
        return obstaclesActivated;
    }
    public static void setObstacles(boolean value) {
        obstaclesActivated = value;
    }
    public static boolean isBombsActivated() {
        return bombsActivated;
    }
    public static void setBombs(boolean value) {
        bombsActivated = value;
    }

    //++++++++++++++++++++++++++++++++++++ switches to menu at the beginning +++++++++++++++++++++++++++++++++++++++++
    public void switchToMenu(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Menu.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);

        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }
    //++++++++++++++++++++++++++++++++++++ starts game +++++++++++++++++++++++++++++++++++++++++
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
    //++++++++++++++++++++++++++++++++++++ switched to info screen how to play +++++++++++++++++++++++++++++++++++++++++
    public void switchToInfo(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Howtoplay.fxml")));
        //stage = (Stage) howtoplay.getScene().getWindow();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("How to play");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();

    }
    //+++++++++++++++++++++++++++++++++ exits whole game when button exit pressed +++++++++++++++++++++++++++++++++++++++
    public void exitGame() throws IOException {
        Alert a = new Alert(Alert.AlertType.NONE);
        a.setAlertType(Alert.AlertType.CONFIRMATION);
        a.setContentText("Do you want to close the game?");
        a.showAndWait();
        System.exit(0);
    }
    //++++++++++++++++++++++++++++++++++++ checks the selected game modes  ++++++++++++++++++++++++++++++++++++++++
    public void whichButtonIsPressed(ActionEvent event) {
        if (event.getSource()==buttonSinglePlayer) {
            buttonSinglePlayer.setStyle("fx-background-color: #50C878");
            multiplayer = false;
        }
        else if (event.getSource()==buttonMultiPlayer) {
            multiplayer = true;
        }
        if (event.getSource()== buttonMulticolor) {
            singleColor = false;
        }
        else if (event.getSource()==buttonOneColor) {
            singleColor = true;
        }
        if (event.getSource()== buttonLightMode) {
            darkMode = false;
        }
        else if (event.getSource()== buttonDarkMode) {
            darkMode = true;
        }
        if (event.getSource()==buttonWithWalls) {
            wallsActivated = false;
        }
        else if (event.getSource()==buttonWithoutWalls) {
            wallsActivated = true;
        }
        if (event.getSource()==buttonWithBombs) {
            bombsActivated = false;
        }
        else if (event.getSource()==buttonWithoutBombs) {
            bombsActivated = true;
        }
        if (event.getSource()==buttonWithObstacles) {
            obstaclesActivated = false;
        }
        else if (event.getSource()==buttonWithoutObstacles) {
            obstaclesActivated = true;
        }
    }
}