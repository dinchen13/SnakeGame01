package com.example.snakegame01;
import javafx.geometry.Bounds;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.example.snakegame01.App.pause;

public class Snake extends Circle{

    private List<Circle> tails;
    private int length;
    private Direction direction;
    private static final int STEP =20;
    private Random random;
    private Color color =Color.FORESTGREEN;
    private Color colorDarkmode=Color.WHITE;
    private Color colortail =Color.LIMEGREEN;
    private Color colortailDarkmode =Color.LIGHTGRAY;


    //Snake erstellen:
    public Snake(double centerX, double centerY,AnchorPane pane, double radius){
        super(centerX, centerY, radius);
        tails=new ArrayList<>();
        direction=Direction.UP;
        random = new Random();
        int red = random.nextInt(255);
        int green = random.nextInt(255);
        int blue = random.nextInt(255);
        super.setFill(Color.rgb(red, green, blue));
        if (MenuController.isSingleColor()) {
            super.setFill(color);
        }
        if(MenuController.isDarkMode()) {
            super.setFill(colorDarkmode);
        }
        pane.getChildren().add(this);
        this.length=0;
    }
    //+++++++++++++++++++++++++++ getter & setters +++++++++++++++++++++++++++++
    public void setPosition(int positionX, int positionY){
        this.setCenterX(positionX);
        this.setCenterY(positionY);
    }
    public void setDirection(Direction direction) {
        this.direction = direction;
    }
    public Direction getDirection() {
        return direction;
    }
    //Länge zurückgeben
    public String getLengthString() {return String.valueOf(this.length);}
    public int getLength() {return tails.size();}//this.length;}
    public Bounds getBoundsOfTail(int index){
        return tails.get(index).getBoundsInLocal();
    }
    //++++++++++++++++++++++++++ bewegen +++++++++++++++++++++++++++++++++++++++
    public void step() {
        if(!pause) {
            for (int i = length - 1; i >= 0; i--) {
                if (i == 0) {
                    tails.get(i).setCenterX(getCenterX());
                    tails.get(i).setCenterY(getCenterY());
                } else {
                    tails.get(i).setCenterX(tails.get(i - 1).getCenterX());
                    tails.get(i).setCenterY(tails.get(i - 1).getCenterY());
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
    }
    public boolean hitFood(Circle food){
        return food.intersects(this.getBoundsInLocal());
    }
    public boolean hitBomb(){
        for (int i = 0; i < Bomb.getNumberOfObstacles(); i++) {
            if (this.intersects(Bomb.getBoundsOfObstacle(i))) {
                System.out.println("hit");
                return true;
            }
        }
        return false;
    }

    private Circle endTail(){
        if (length==0){
            return this;
        }
        return tails.get(length-1);
    }
    public void removeTails(AnchorPane pane) { //funkt nicht
        tails.clear();
        pane.getChildren().remove(tails);
    }
    public void eat(Circle food){
        Circle tail = endTail();
        food.setCenterX(tail.getCenterX());
        food.setCenterY(tail.getCenterY());
        int red = random.nextInt(255);
        int green = random.nextInt(255);
        int blue = random.nextInt(255);
        food.setFill(Color.rgb(red, green, blue));
        if (MenuController.isSingleColor()) {
            food.setFill(colortail);
        }
        if(MenuController.isDarkMode()){
            food.setFill(colortailDarkmode);
        }
        tails.add(food); // tails.add(length, food);
        length++;
        System.out.println("length = "+length);
    }

    public void decrease(AnchorPane pane){
        if(length==0){}
        else {
            System.out.println(tails);
            pane.getChildren().remove(tails.get(length-1));
            tails.remove(tails.get(length-1));
            System.out.println(tails);
            length--;
            }
    }

    public boolean eatSelf(){
        for (int i =1; i<length; i++){
            if(this.getCenterX()==tails.get(i).getCenterX()&&this.getCenterY()==tails.get(i).getCenterY()){
                //geht das ganze mit intersects auch?
                return true;
            }
        }
        return false;
    }
}