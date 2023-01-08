package com.example.snakegame01;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import java.util.ArrayList;
import java.util.Random;
public class Food extends Circle {
    private int radius =8;
    private Circle food;
    private Random random = new Random();
    private int positionX;
    private int positionY;
    private Color color =Color.DARKRED;
    private AnchorPane pane;

    public Food(double positionX, double positionY, AnchorPane pane, double radius){
        super(positionX, positionY, radius);
        this.pane = pane;
        this.radius=(int) radius;
        super.setFill(color);
        pane.getChildren().add(this);
    }
    public int getPositionX() {
        return positionX;
    }
    public int getPositionY() {
        return positionY;
    }
    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }
    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }
    public void moveFood(){
        randomLocationFood();
    }
    public void randomLocationFood(){
        int positionX = 50;//random.nextInt(460)+100+radius;
        int positionY = 50;//random.nextInt(460)+20+radius;
        setPositionX(positionX);
        setPositionY(positionY);
    }
}