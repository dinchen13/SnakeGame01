package com.example.snakegame01;
import javafx.geometry.Bounds;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//---------------------------------------------------------------------------------------------
// Bomb object
// Methods: getter/setter, moveLocation, randomLocationObstacle, (deleteBomb), deleteAll
//---------------------------------------------------------------------------------------------

public class Bomb extends Circle {
    private int radius;
    private Random random = new Random();
    private Color color =Color.DARKGRAY;
    private Color colorDarkmode =Color.DIMGRAY;
    private static List<Circle> bombs =new ArrayList<>();

    //++++++++++++++++++++++++++++++++++++++++++ constructor +++++++++++++++++++++++++++++++++++++++++++++
    public Bomb(double positionX, double positionY, AnchorPane pane, double radius){
        super(positionX, positionY, radius);
        this.radius=(int) radius;
        super.setFill(color);
        if(MenuController.isDarkMode()){
            super.setFill(colorDarkmode);
        }
        pane.getChildren().add(this);
        bombs.add(this);
    }
    //++++++++++++++++++++++++++++++++++++ getter & setters +++++++++++++++++++++++++++++++++++++++++
    public void setPositionX(int positionX) {
        this.setCenterX(positionX);
    }
    public void setPositionY(int positionY) {this.setCenterY(positionY);}
    public static int getNumberOfObstacles(){
        return bombs.size();
    }
    public static Bounds getBoundsOfObstacle(int index){
        return bombs.get(index).getBoundsInLocal();
    }

    //++++++++++++++++ moves obstacle away because of intersection with other objects ++++++++++++++++++++
    //------------------------------------is called in class App ----------------------------------------
    public void moveLocation(){
        randomLocationObstacle();
    }
    //++++++++++++++++++++++++++++++++++++finds random location ++++++++++++++++++++++++++++++++++++++++++
    public void randomLocationObstacle(){
        int x =random.nextInt(560-radius)+100+radius;
        int y =random.nextInt(560-radius)+20+radius;
        setPositionX(x);
        setPositionY(y);
    }
    //------------------------------------wasn´t implemented ----------------------------------------
    public static void deleteBomb(int index, AnchorPane pane){
        pane.getChildren().remove(index);
        bombs.remove(index);
    }
    //++++++++++++++++++++++++++++++++deletes all bombs after gameOver ++++++++++++++++++++++++++++++++++++
    public static void deleteAll(){
        bombs.clear();
    }
}
