package com.example.snakegame01;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.Random;
public class Food extends Circle {
    private int radius;
    private Circle food;
    private Random random = new Random();
    private int positionX;
    private int positionY;
    private Color color =Color.DARKRED;
    private Color colorDarkmode =Color.LIGHTPINK;

    public Food(double positionX, double positionY, AnchorPane pane, double radius){
        super(positionX, positionY, radius);
        this.radius=(int) radius;
        super.setFill(color);
        if(MenuController.isDarkmode()){
            super.setFill(colorDarkmode);
        }
        pane.getChildren().add(this);
    }
    public int getPositionX() {
        return positionX;
    }
    public int getPositionY() {
        return positionY;
    }
    public void setPositionX(int positionX) {
        this.setCenterX(positionX);
    }
    public void setPositionY(int positionY) {
        this.setCenterY(positionY);;
    }
    public void moveLocation(){
        randomLocationFood();
    }
    public void randomLocationFood(){
        int x =random.nextInt(560-radius*2)+100+radius;
        int y =random.nextInt(560-radius*2)+20+radius;
        setPositionX(x);
        setPositionY(y);
    }
}