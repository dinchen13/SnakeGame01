package com.example.snakegame01;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import java.util.Random;

//---------------------------------------------------------------------------------------------
// Food object
// Methods: getter/setter, moveLocation, randomLocationObstacle
//---------------------------------------------------------------------------------------------

public class Food extends Circle {
    private int radius;
    private Random random = new Random();
    private Color color =Color.DARKRED;
    private Color colorDarkmode =Color.LIGHTPINK;

    //++++++++++++++++++++++++++++++++++++++++++ constructor +++++++++++++++++++++++++++++++++++++++++++++
    public Food(double positionX, double positionY, AnchorPane pane, double radius){
        super(positionX, positionY, radius);
        this.radius=(int) radius;
        super.setFill(color);
        if(MenuController.isDarkMode()){
            super.setFill(colorDarkmode);
        }
        pane.getChildren().add(this);
    }
    //++++++++++++++++++++++++++++++++++++ getter & setters +++++++++++++++++++++++++++++++++++++++++
    public void setPositionX(int positionX) {
        this.setCenterX(positionX);
    }
    public void setPositionY(int positionY) {this.setCenterY(positionY);}

    //++++++++++++++++ moves food away because of intersection with other objects ++++++++++++++++++++
    //------------------------------------is called in class App ----------------------------------------
    public void moveLocation(){
        randomLocationFood();
    }
    public void randomLocationFood(){
        //++++++++++++++++++++++++++++++++++++finds random location ++++++++++++++++++++++++++++++++++++++++++
        int x =random.nextInt(560-radius*2)+100+radius;
        int y =random.nextInt(560-radius*2)+20+radius;
        setPositionX(x);
        setPositionY(y);
    }
}