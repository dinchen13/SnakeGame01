package com.example.snakegame01;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Random;

public class AppController extends Application {
    @FXML
    private Button pause;
    @FXML
    private Button menu;
    @FXML
    private Label score2;
    @FXML
    private Pane root;

    private Stage stage;
    private Scene scene;


    public void switchToScene1(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("menu.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }



        private static final int WIDTH =680;
        private static final int HEIGHT =600;
        private static final int RADIUS =8;

        private Snake snake;
        private Circle food;
        private int speed;
        @FXML
        private Text score;
        private Random random;

        //METHODEN:
        //Snake erstellen:
        private void newSnake(){
            snake=new Snake(580/2+100, 580/2, RADIUS+3);
            root.getChildren().add(snake);
            speed=200;
        }
        //Food erstellen:
        public void newFood(){
            food = new Circle(random.nextInt(460)+100+RADIUS,random.nextInt(460)+20+RADIUS,RADIUS); //da muss man noch was adjusten
            food.setFill(Color.DARKRED);
            root.getChildren().add(food);
            food.toFront();
        }
        //Bildschirm anpassen
        private void adjustLocation(int WIDTH, int HEIGHT){
            //Wenns ausn Bildschrim raus geht:
            if(snake.getCenterX()>WIDTH-20){
                snake.setCenterX(100+RADIUS+3);
            } else if(snake.getCenterX()<100){
                snake.setCenterX(WIDTH-20-RADIUS-3);
            }
            if(snake.getCenterY()>HEIGHT-20-RADIUS-3){
                snake.setCenterY(20+RADIUS+3);
            } else if(snake.getCenterY()<20){
                snake.setCenterY(HEIGHT-20-RADIUS-3);
            }
        }
        //bewegen (snake)
        public void move(int WIDTH, int HEIGHT){
            Platform.runLater(()-> {            //braucht man wenn ein anderer Thread (other than the creator) Änderungen machen könnte
                snake.step();
                adjustLocation(WIDTH, HEIGHT);
                if(gameOver()){
                    snake.setLength(0);
                    snake.removeTails();
                    root.getChildren().add(score);
                    score.setText("Game Over\n Score:"+ snake.getLengthString());
                    newSnake();
                    newFood();
                }
                else if(snake.hitFood(food)){
                    snake.eat(food);
                    if (snake.getLength()<=10){speed=speed-7;}
                    else if (snake.getLength()<=20){speed=speed-4;}
                    else if (snake.getLength()>20){speed=speed-1;}
                    score.setText(""+snake.getLengthString());
                    newFood();
                }
            });
        }
        private boolean gameOver(){
            return snake.eatSelf();
        }

        //PROGRAMM:
        @Override
        public void start(Stage stage) throws IOException {
            //Bildschirm laden:
            root= new Pane();
            root = FXMLLoader.load(App.class.getResource("Game.fxml"));
            // FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Game.fxml"));
            //Scene scene = new Scene(fxmlLoader.load(), 680, 600);



            //Bildschirm erzeugen:
            /*root= new Pane();
            root.setPrefSize(WIDTH, HEIGHT);*/
            score= new Text(48,85,"0");
            root.getChildren().add(score);
            random = new Random();
            //Snake machen:
            newSnake();
            //Food machen:
            newFood();

            //Was ist Runnable?
            Runnable r = () -> {
                try {
                    for (;;){
                        move(WIDTH, HEIGHT);
                        Thread.sleep(speed);
                    }
                }catch (InterruptedException ie){
                }
            };
            //Scene setzten:
            Scene scene = new Scene(root);
            //Color c = Color.rgb( 230, 255, 210);
            //scene.setFill(c);
            //Tasten drücken:
            scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
                KeyCode code = event.getCode();
                if ((code == KeyCode.W)&&((snake.getDirection()!=Direction.DOWN)||(snake.getLength()==0))){
                    snake.setDirection(Direction.UP);
                } else if ((code == KeyCode.S)&&((snake.getDirection()!=Direction.UP)||(snake.getLength()==0))) {
                    snake.setDirection(Direction.DOWN);
                } else if ((code == KeyCode.A)&&((snake.getDirection()!=Direction.RIGHT)||(snake.getLength()==0))){
                    snake.setDirection(Direction.LEFT);
                } else if ((code == KeyCode.D)&&((snake.getDirection()!=Direction.LEFT)||(snake.getLength()==0))) {
                    snake.setDirection(Direction.RIGHT);
                }
            });

            //Bildschirmeinstellungen:
            stage.setTitle("snake");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();

            //Thread starten:
            Thread th = new Thread(r);
            th. setDaemon(true);
            th.start();
        }
        //MMMAAAAIIIIINNNN:
        public static void main(String[] args) {
            launch();
        }
    }