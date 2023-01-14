package com.example.snakegame01;

import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Random;

public class Obstacle extends Rectangle {

    private int size;
    private Rectangle obstacle;
    private Random random = new Random();
    private int positionX;
    private int positionY;
    private Color color =Color.BURLYWOOD;
    private Color colorDarkmode =Color.SNOW;

    public Obstacle(double positionX, double positionY, AnchorPane pane, double size){
        super(positionX, positionY, size,size);
        this.size=(int) size;
        super.setFill(color);
        if(MenuController.isDarkmode()){
            super.setFill(colorDarkmode);
        }
        pane.getChildren().add(this);
    }
    public void setPositionX(int positionX) {
        this.setX(positionX);
    }
    public void setPositionY(int positionY) {
        this.setY(positionY);;
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





}
