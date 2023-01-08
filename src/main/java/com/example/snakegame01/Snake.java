package com.example.snakegame01;
import javafx.geometry.Bounds;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Snake extends Circle{

    private List<Circle> tails;
    private int length=0;
    private Direction direction;
    private static final int STEP =20;
    private Random random;
    private AnchorPane pane;


    //Snake erstellen:
    public Snake(double centerX, double centerY,AnchorPane pane, double radius){
        super(centerX, centerY, radius);
        tails=new ArrayList<>();
        direction=Direction.UP;
        random = new Random();
        int red = random.nextInt(255);
        int green = random.nextInt(255);
        int blue = random.nextInt(255);
        setFill(Color.rgb(red, green, blue));
        this.pane = pane;
        pane.getChildren().add(this);
        this.length=0;
    }

    //direction ändern
    public void setDirection(Direction direction) {
        this.direction = direction;
    }
    public Direction getDirection() {
        return direction;
    }
    //Länge zurückgeben
    public String getLengthString() {return String.valueOf(this.length);}
    public int getLength() {return this.length;}
    public void setLength(int length) {this.length = length;}
    public void removeTails() { //funkt nicht
        tails.clear();
    }
    public int getTailPositionX(int index){
        return (int) tails.get(index).getCenterX();
    }
    public int getTailPositionY(int index){
        return (int) tails.get(index).getCenterY();
    }
    public Bounds getBoundsOfTail(int index){
        return tails.get(index).getBoundsInLocal();
    }
//bewegen:
    public void step() {
        for(int i = length-1; i>=0; i--){
            if(i==0){
                tails.get(i).setCenterX(getCenterX());
                tails.get(i).setCenterY(getCenterY());
            }else {
                tails.get(i).setCenterX(tails.get(i-1).getCenterX());
                tails.get(i).setCenterY(tails.get(i-1).getCenterY());
            }
        }
        //Tasten drücken:
        if (direction == Direction.UP) {
            this.setCenterY(this.getCenterY() - STEP);
        } else if (direction == Direction.DOWN) {
            this.setCenterY(this.getCenterY() + STEP);
        } else if (direction == Direction.LEFT) {
            this.setCenterX(this.getCenterX() - STEP);
        } else if (direction == Direction.RIGHT) {
            this.setCenterX(this.getCenterX() + STEP);
        }
    }
    public boolean hitFood(Circle food){
        return food.intersects(this.getBoundsInLocal());
    }
    private Circle endTail(){
        if (length==0){
            return this;
        }
        return tails.get(length-1);
    }
    public void eat(Circle food){
        Circle tail = endTail();
        food.setCenterX(tail.getCenterX());
        food.setCenterY(tail.getCenterY());
        int red = random.nextInt(255);
        int green = random.nextInt(255);
        int blue = random.nextInt(255);
        food.setFill(Color.rgb(red, green, blue));
        tails.add(length++, food);
    }

    public boolean eatSelf(){
        for (int i =0; i<length; i++){
            if(this.getCenterX()==tails.get(i).getCenterX()&&this.getCenterY()==tails.get(i).getCenterY()){
                //geht das ganze mit intersects auch?
                return true;
            }
        }
        return false;
    }
}