package com.example.snakegame01;

import javafx.geometry.Bounds;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Bomb extends Circle {

    private int radius;
    private Circle bomb;
    private Random random = new Random();
    private int positionX;
    private int positionY;
    private Color color =Color.DARKGRAY;
    private Color colorDarkmode =Color.DIMGRAY;
    private static List<Circle> bombs =new ArrayList<>();


    public Bomb(double positionX, double positionY, AnchorPane pane, double radius){
        super(positionX, positionY, radius);
        this.radius=(int) radius;
        super.setFill(color);
        if(MenuController.isDarkmode()){
            super.setFill(colorDarkmode);
        }
        pane.getChildren().add(this);
        bombs.add(this);
    }
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

    public void moveLocation(){
        randomLocationObstacle();
    }
    public void randomLocationObstacle(){
        int x =random.nextInt(560-radius)+100+radius;
        int y =random.nextInt(560-radius)+20+radius;
        setPositionX(x);
        setPositionY(y);
    }
    public static void deleteAll(){
        bombs.clear();
    }

}
