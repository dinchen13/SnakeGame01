package com.example.snakegame01;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

    public class App extends Application {

        @FXML
        private Text score;
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
        private boolean pause =false;
        private boolean gameOver =false;

        public void switchToMenu(ActionEvent event) throws IOException {
            root = FXMLLoader.load(getClass().getResource("menu.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
            //hier noch ne eigene classe oder methode schreiben die das alles zurücksetzt
            MenuController.setMulti(false);
            MenuController.setColor(false);
            MenuController.setBackground(false);
        }

        public void reload(ActionEvent event) throws IOException {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Game.fxml")));
            //stage = (Stage) start.getScene().getWindow();
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setTitle("Snake");
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();

            //launch Game:
            App Snake = new App();
            Snake.start(stage);
        }

        public void MakePause(){
            if (!pause){pause=true;}
            else if (pause){pause=false;}
            System.out.println(pause);
            System.out.println("boah");
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
                while (FoodInsideSnake(snake)) {
                    food.moveFood();
                }
            }
        }
        private boolean FoodInsideSnake(Snake snake){
            for (int i = 0; i < snake.getLength(); i++) {
                if((food.intersects(snake.getBoundsInLocal()))||(food.intersects(snake.getBoundsOfTail(i)))){
                    System.out.println("moveeee fooood");
                    return true;
                }
            }
            return false;
        }

        //Bildschirm anpassen
        private void handleSnakeOutsideWalls(Snake snake) {
            //Wenns ausn Bildschrim raus geht:
            if (snake.getCenterX() > WIDTH - 20) {
                if (!MenuController.isWallsActivated()) {
                snake.setCenterX(100 + RADIUS + 3);}
                else{gameOver=true;}
            } else if (snake.getCenterX() < 100) {
                if (!MenuController.isWallsActivated()) {
                snake.setCenterX(WIDTH - 20 - RADIUS - 3);}
                else{gameOver=true;}
            }
            if (snake.getCenterY() > HEIGHT - 20 - RADIUS - 3) {
                if (!MenuController.isWallsActivated()) {
                snake.setCenterY(20 + RADIUS + 3);}
                else{gameOver=true;}
            } else if (snake.getCenterY() < 20) {
                    if (!MenuController.isWallsActivated()) {
                snake.setCenterY(HEIGHT - 20 - RADIUS - 3);}
                else{gameOver=true;}
            }
        }

        //bewegen (snake)
        public void updateGame(){
            Platform.runLater(()-> {            //braucht man wenn ein anderer Thread (other than the creator) Änderungen machen könnte
                snake.step();
                handleSnakeOutsideWalls(snake);
                if(twoPlayer){snake2.step();}
                if(twoPlayer){
                    handleSnakeOutsideWalls(snake2);}
                if(checkIfGameOver()){
                    try {
                        root = FXMLLoader.load(App.class.getResource("Game.fxml"));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    score.setText("Game Over");
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
        private boolean checkIfGameOver(){
            if(MenuController.isWallsActivated()&&gameOver){
                return true;
            }
            else if (twoPlayer){
                //if(snake.eatSelf()){return true;}
                if(snake2.eatSelf()){return true;}
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
            root = FXMLLoader.load(App.class.getResource("Game.fxml"));
            Rectangle rect = new Rectangle(100, 20, 560, 560);
            Color c= Color.rgb(58, 14, 14);
            rect.setFill(c);
            root.getChildren().add(rect);
            rect.setVisible(false);
            if(MenuController.isDarkmode()){
                rect.setVisible(true);
            }
            score= new Text(48,85,"0");
            root.getChildren().add(score);
            random = new Random();

            twoPlayer=MenuController.isMultiplayer();
            newSnake();
            newFood();

            //Scene setzten:
            Scene scene = new Scene(root);


            //Was ist Runnable? ein interface,
            Runnable r = () -> {
                try {
                    for (;;){
                        updateGame();
                        Thread.sleep(speed);
                    }
                }catch (InterruptedException ie){
                }
            };

            //Tasten drücken:
            scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
                KeyCode code = event.getCode();
                if ((code == KeyCode.UP)&&((snake.getDirection()!=Direction.DOWN)||(snake.getLength()==0))){
                    snake.setDirection(Direction.UP);
                } else if ((code == KeyCode.DOWN)&&((snake.getDirection()!=Direction.UP)||(snake.getLength()==0))) {
                    snake.setDirection(Direction.DOWN);
                } else if ((code == KeyCode.LEFT)&&((snake.getDirection()!=Direction.RIGHT)||(snake.getLength()==0))){
                    snake.setDirection(Direction.LEFT);
                } else if ((code == KeyCode.RIGHT)&&((snake.getDirection()!=Direction.LEFT)||(snake.getLength()==0))) {
                    snake.setDirection(Direction.RIGHT);
                }
                if(twoPlayer) {
                    if ((code == KeyCode.W) && ((snake2.getDirection() != Direction.DOWN) || (snake2.getLength() == 0))) {
                        snake2.setDirection(Direction.UP);
                    } else if ((code == KeyCode.S) && ((snake2.getDirection() != Direction.UP) || (snake2.getLength() == 0))) {
                        snake2.setDirection(Direction.DOWN);
                    } else if ((code == KeyCode.A) && ((snake2.getDirection() != Direction.RIGHT) || (snake2.getLength() == 0))) {
                        snake2.setDirection(Direction.LEFT);
                    } else if ((code == KeyCode.D) && ((snake2.getDirection() != Direction.LEFT) || (snake2.getLength() == 0))) {
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

    //i love youuu kevin