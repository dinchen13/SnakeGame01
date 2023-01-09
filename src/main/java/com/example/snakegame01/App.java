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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Random;

    public class App extends Application {
        //for SceneBuilder:
        @FXML
        private Button pause;
        @FXML
        private Button menu;
        @FXML
        private Text score;
        @FXML
        private Pane root;
        private Stage stage;
        private Scene scene;
        Thread th;
        private static final int WIDTH =680;
        private static final int HEIGHT =600;
        private static final int RADIUS =8;
        private Snake snake;
        private Snake snake2;
        private Food food;
        private int speed;
        private Random random;
        private boolean twoPlayer =false;

        public void switchToScene1(ActionEvent event) throws IOException {
            root = FXMLLoader.load(getClass().getResource("menu.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

        //METHODEN:
        //Snake erstellen:
        private void newSnake(){
            if(twoPlayer) {
                snake=new Snake(580/2+100 - 200, 580/2+200, (AnchorPane) root, RADIUS+3);
                snake2 = new Snake(580/2+100+200, 580/2-200, (AnchorPane) root, RADIUS + 3);
                snake2.setDirection(Direction.DOWN);
            }
            else{
                snake=new Snake(580/2+100, 580/2, (AnchorPane) root, RADIUS+3);
            }
            speed = 200;
        }

        //Food erstellen:
        public void newFood(){
            food = new Food(random.nextInt(460)+100+RADIUS,random.nextInt(460)+20+RADIUS, (AnchorPane) root,RADIUS);
            if(FoodInsideSnake(snake)){moveFoodAway();}
            if(twoPlayer&&FoodInsideSnake(snake2)){moveFoodAway();}
        }
        //adjust food
        private void moveFoodAway(){
            food.moveFood();
            if(twoPlayer){
                while (FoodInsideSnake(snake)|| FoodInsideSnake(snake2)){
                    food.moveFood();
                }
            }
            else {
                while (FoodInsideSnake(snake)){
                    food.moveFood();
                }
            }
        }
        private boolean FoodInsideSnake(Snake snake){
            for (int i = 0; i < snake.getLength(); i++) {
                if((food.intersects(snake.getBoundsInLocal()))||(food.intersects(snake.getBoundsOfTail(i)))){
                    //System.out.println("moveeee fooood");
                    return true;
                }
            }
            return false;
        }

        //Bildschirm anpassen
        private void adjustLocation(Snake snake){
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
        public void move(){
            Platform.runLater(()-> {            //braucht man wenn ein anderer Thread (other than the creator) Änderungen machen könnte
                snake.step();
                if(twoPlayer){snake2.step();}
                adjustLocation(snake);
                if(twoPlayer){adjustLocation(snake2);}
                if(gameOver()){
                    try {
                        root = FXMLLoader.load(App.class.getResource("Game.fxml"));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
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
                else if (twoPlayer&&(snake2.hitFood(food))){
                    snake2.eat(food);
                    if (snake2.getLength()<=10){speed=speed-7;}
                    else if (snake2.getLength()<=20){speed=speed-4;}
                    else if (snake2.getLength()>20){speed=speed-1;}
                    score.setText(""+snake2.getLengthString());
                    newFood();
                }
            });
        }
        private boolean gameOver(){
            if (twoPlayer){
                if(snake.eatSelf()){return true;}
                else if(snake2.eatSelf()){return true;}
                else if(snake.intersects(snake2.getBoundsInLocal())){return true;}
                else if(snake2.intersects(snake.getBoundsInLocal())){return true;}
                else{return false;}
           }
            else {
                return snake.eatSelf();
            }
        }

        //PROGRAMM:
        @Override
        public void start(Stage stage) throws IOException {
            //Bildschirm laden:
            root= new Pane();
            root = FXMLLoader.load(App.class.getResource("Game.fxml"));
            //FXMLLoader scene01 = new FXMLLoader(HelloApplication.class.getResource("Game.fxml"));
            //Scene scene = new Scene(scene01.load(), 680, 600);

            //Bildschirm erzeugen:
            /*root= new Pane();
            root.setPrefSize(WIDTH, HEIGHT);*/
            score= new Text(48,85,"0");
            root.getChildren().add(score);
            random = new Random();

            twoPlayer=MenuController.getMulti();
            newSnake();
            newFood();

            //Was ist Runnable? ein interface,
            Runnable r = () -> {
                try {
                    for (;;){
                        move();
                        //if(twoPlayer){move(WIDTH, HEIGHT,snake2);}

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
                if(twoPlayer) {
                    if ((code == KeyCode.UP) && ((snake2.getDirection() != Direction.DOWN) || (snake2.getLength() == 0))) {
                        snake2.setDirection(Direction.UP);
                    } else if ((code == KeyCode.DOWN) && ((snake2.getDirection() != Direction.UP) || (snake2.getLength() == 0))) {
                        snake2.setDirection(Direction.DOWN);
                    } else if ((code == KeyCode.LEFT) && ((snake2.getDirection() != Direction.RIGHT) || (snake2.getLength() == 0))) {
                        snake2.setDirection(Direction.LEFT);
                    } else if ((code == KeyCode.RIGHT) && ((snake2.getDirection() != Direction.LEFT) || (snake2.getLength() == 0))) {
                        snake2.setDirection(Direction.RIGHT);
                    }
                }
            });

            //Bildschirmeinstellungen:
            stage.setTitle("snake");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();

            //Thread starten:
            th = new Thread(r);
            th. setDaemon(true);
            th.start();
        }
        //MMMAAAAIIIIINNNN:
        public static void main(String[] args) {
            launch();
        }
    }