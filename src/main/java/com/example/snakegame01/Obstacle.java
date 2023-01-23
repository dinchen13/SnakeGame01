package com.example.snakegame01;

import javafx.geometry.Bounds;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.util.ArrayList;
import java.util.List;

import java.util.Random;

public class Obstacle extends Rectangle {

    private int size;
    private Rectangle obstacle;
    private Random random = new Random();
    private int positionX;
    private int positionY;
    private Color color =Color.BLACK;
    private Color colorDarkmode =Color.SNOW;
    private static List<Rectangle> obstacles =new ArrayList<>();


    public Obstacle(double positionX, double positionY, AnchorPane pane, double size){
        super(positionX, positionY, size,size);
        this.size=(int) size;
        super.setFill(color);
        if(MenuController.isDarkMode()){
            super.setFill(colorDarkmode);
        }
        pane.getChildren().add(this);
        obstacles.add(this);
    }
    public void setPositionX(int positionX) {
        this.setX(positionX);
    }
    public void setPositionY(int positionY) {
        this.setY(positionY);;
    }
    public static int getNumberOfObstacles(){
        return obstacles.size();
    }
    public static Bounds getBoundsOfObstacle(int index){
            return obstacles.get(index).getBoundsInLocal();
    }

    public void moveLocation(){
        randomLocationObstacle();
    }
    public void randomLocationObstacle(){
        int x =random.nextInt(560-size)+100+size;
        int y =random.nextInt(560-size)+20+size;
        setPositionX(x);
        setPositionY(y);
    }
    public static void deleteAll(){
        obstacles.clear();
    }

}
