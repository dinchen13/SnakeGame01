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
import javafx.scene.shape.Shape;
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
        private static final int SIZE =RADIUS*3;
        private Snake snake;
        private Snake snake2;
        private Food food;
        private Obstacle obstacle;
        private Bomb bomb;
        private int speed;
        private Random random;
        private boolean twoPlayer =false;
        public static boolean pause =false;
        private static boolean gameOverDueScreen =false;
        private static int openOnlyOnce =1;

    //+++++++++++++++++++++++++++ swichtes back to the menu when menu button gets pressed ++++++++++++++++++++++++++
        public void switchToMenu(ActionEvent event) throws IOException {
            root = FXMLLoader.load(getClass().getResource("Menu.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
            MenuController.setMulti(false);
            MenuController.setColor(false);
            MenuController.setBackground(false);
            MenuController.setWalls(false);
            MenuController.setObstacles(false);
            MenuController.setBombs(false);
            setToStartValues();
        }
    //++++++++++++++++++++++++++++++++++++++++++++ reloads the game +++++++++++++++++++++++++++++++++++++++++++++++
        public void reload(ActionEvent event) throws IOException {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Game.fxml")));
            //stage = (Stage) start.getScene().getWindow();
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setTitle("Snake");
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
            setToStartValues();

            //launch Game:
            App Snake = new App();
            Snake.start(stage);
        }
    //+++++++++++++++++++++++++++++++++++ pauses when pause button gets pressed +++++++++++++++++++++++++++++++++++
        public void makePause(){
            if (!pause){pause=true;}
            else if (pause){pause=false;}
            System.out.println("Pause");
        }
    //++++++++++++++++++++++++++++++++++++++ when game is over, GameOver screen opens ++++++++++++++++++++++++++++++++
        public void switchToGameOver() throws IOException {
            if(openOnlyOnce>=1) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("GameOver.fxml"));
                root = loader.load();
                scene = new Scene(root);
                stage = new Stage();
                stage.setResizable(false);
                stage.centerOnScreen();
                stage.setScene(scene);
                stage.show();
            }
        }
    //+++++++++++++++++++++++++++ sets values that might have change to their start value ++++++++++++++++++++++++++
    //----------------------------------------is used when scene is switched----------------------------------------
        public static void setToStartValues(){
            gameOverDueScreen =false;
            Obstacle.deleteAll();
            Bomb.deleteAll();
            openOnlyOnce=1;
        }

    //++++++++++++++++++++++++++++++++++++++ makes new Snake object ++++++++++++++++++++++++++++++++++++++++
        private void newSnake(){
            if(twoPlayer) {
                snake = new Snake(580/2+100+200, 580/2-200, (AnchorPane) root, RADIUS + 3);
                snake2=new Snake(580/2+100 - 200, 580/2+200, (AnchorPane) root, RADIUS+3);
                snake2.setDirection(Direction.DOWN);
            }
            else{
                snake=new Snake(580/2+100, 580/2, (AnchorPane) root, RADIUS+3);
            }
            speed = 200;
        }
    //++++++++++++++++++++++++++++++++++++++ makes new Food object ++++++++++++++++++++++++++++++++++++++++
        public void newFood(){
            food = new Food(random.nextInt(560-RADIUS*2)+100+RADIUS,random.nextInt(560-RADIUS*2)+20+RADIUS, (AnchorPane) root,RADIUS);
            if(isInsideSnake(snake,food)){moveFoodAway();}
            if(twoPlayer&& isInsideSnake(snake2,food)){moveFoodAway();}
            System.out.println("make Food");
        }
    //+++++++++++++++++ changes location of food if it spawns inside another object ++++++++++++++++++++++
        private void moveFoodAway(){
            food.moveLocation();
            if(twoPlayer){
                while (isInsideSnake(snake,food)|| isInsideSnake(snake2,food)||isInsideObstacle(food)||isInsideBomb(food)){
                    food.moveLocation();
                }
            }
            else {
                while (isInsideSnake(snake,food)||isInsideObstacle(food)||isInsideBomb(food)) {
                    food.moveLocation();
                }
            }
        }
    //++++++++++++++++++++++++++++++++++++++ makes new Obstacle object ++++++++++++++++++++++++++++++++++++++++
        public void newObstacle(){
            if(MenuController.isObstaclesActivated()){
            obstacle = new Obstacle(random.nextInt(560-SIZE*2)+100+SIZE,random.nextInt(560-SIZE*2)+20+SIZE, (AnchorPane) root,SIZE);
            if(isInsideSnake(snake,obstacle)){moveObstacleAway();}
            if(twoPlayer&&isInsideSnake(snake2,obstacle)){moveObstacleAway();}
                System.out.println("make Wall");
            }
        }
    //+++++++++++++++++ changes location of obstacle if it spawns inside another object ++++++++++++++++++++++
        private void moveObstacleAway(){
            obstacle.moveLocation();
            if(twoPlayer){
                while (isInsideSnake(snake,obstacle)|| isInsideSnake(snake2,obstacle)||isInsideFood(obstacle)||isInsideBomb(obstacle)){
                    obstacle.moveLocation();
                }
            }
            else {
                while (isInsideSnake(snake,obstacle)||isInsideFood(obstacle)||isInsideBomb(obstacle)) {
                    obstacle.moveLocation();
                }
            }
        }
    //++++++++++++++++++++++++++++++++++++++ makes new Bomb object ++++++++++++++++++++++++++++++++++++++++
        public void newBomb(){
            if(MenuController.isBombsActivated()) {
                bomb = new Bomb(random.nextInt(560 - RADIUS * 2) + 100 + RADIUS, random.nextInt(560 - RADIUS * 2) + 20 + RADIUS, (AnchorPane) root, RADIUS);
                if (isInsideSnake(snake, bomb)) {
                    moveBombAway();
                }
                if (twoPlayer && isInsideSnake(snake2, bomb)) {
                    moveBombAway();
                }
                System.out.println("make Bomb");
            }
        }
    //+++++++++++++++++ changes location of bomb if it spawns inside another object ++++++++++++++++++++++
        private void moveBombAway(){
            bomb.moveLocation();
            if(twoPlayer){
                while (isInsideSnake(snake,bomb)|| isInsideSnake(snake2,bomb)||isInsideFood(bomb)){
                    bomb.moveLocation();
                }
            }
            else {
                while (isInsideSnake(snake,bomb)||isInsideFood(bomb)) {
                    bomb.moveLocation();
                }
            }
        }
        // ++++++++++++++ sees if an object is spawned inside/over another object (as this isn´t wished) +++++++++++++++++
        private boolean isInsideSnake(Snake snake, Shape shape){
            for (int i = 0; i < snake.getLength(); i++) {
                if((shape.intersects(snake.getBoundsInLocal()))||(shape.intersects(snake.getBoundsOfTail(i)))){
                    System.out.println("moveeee things 0");
                    return true;
                }
            }
            return false;
        }
        private boolean isInsideFood(Shape shape){
            if (shape.intersects(food.getBoundsInLocal())) {
                System.out.println("moveeee things 1");
                return true;
            }
            return false;
        }
        private boolean isInsideObstacle(Shape shape){
            for (int i = 0; i < Obstacle.getNumberOfObstacles(); i++) {
                if (shape.intersects(Obstacle.getBoundsOfObstacle(i))) {
                System.out.println("moveeee things 2");
                return true;
                }
            }
            return false;
        }
        private boolean isInsideBomb(Shape shape){
            for (int i = 0; i < Bomb.getNumberOfObstacles(); i++) {
                if (shape.intersects(Bomb.getBoundsOfObstacle(i))) {
                    System.out.println("moveeee things 3");
                    return true;
                }
            }
            return false;
        }
        //+++++++++++++++++++++++++++adjust screen or snake dies if walls activated+++++++++++++++++++++++++++++++++++++
        private void handleSnakeOutsideWalls(Snake snake) {
            //Wenns ausn Bildschrim raus geht:
            if (snake.getCenterX() > WIDTH - 20) {
                if (MenuController.isWallsActivated()) {
                    gameOverDueScreen =true;}
                else{snake.setCenterX(100 + RADIUS + 3);}
            } else if (snake.getCenterX() < 100) {
                if (MenuController.isWallsActivated()) {
                    gameOverDueScreen =true;}
                else{snake.setCenterX(WIDTH - 20 - RADIUS - 3);}
            }
            if (snake.getCenterY() > HEIGHT - 20 - RADIUS - 3) {
                if (MenuController.isWallsActivated()) {
                    gameOverDueScreen =true;}
                else{snake.setCenterY(20 + RADIUS + 3);}
            } else if (snake.getCenterY() < 20) {
                if (MenuController.isWallsActivated()) {
                    gameOverDueScreen =true;}
                else{snake.setCenterY(HEIGHT - 20 - RADIUS - 3);}
            }
        }
        //+++++++++++++++++++++++++++checks if game is lost with different scenarios++++++++++++++++++++++++++++++++++++
        private boolean checkIfGameOver(){
            if(MenuController.isWallsActivated()&& gameOverDueScreen){
                System.out.println("die 1");                                        //die because of walls
                return true;
            }
            if((MenuController.isObstaclesActivated())&&snake.getLength()>=2) {
                for (int i = 0; i < Obstacle.getNumberOfObstacles(); i++) {
                    if (snake.intersects(Obstacle.getBoundsOfObstacle(i))) {
                        System.out.println("die 2");                                        //die because of obstacles
                        return true;
                    }
                }
            }
            if (twoPlayer){
                if((MenuController.isObstaclesActivated())&&snake2.getLength()>=2) {
                    for (int i = 0; i < Obstacle.getNumberOfObstacles(); i++) {
                        if (snake2.intersects(Obstacle.getBoundsOfObstacle(i))) {
                            System.out.println("die 3");                              //die because of obstacles snake 2
                            return true;
                        }
                    }
                }
                if(snake2.checkIfEatSelf()){System.out.println("die 4");return true;}                 //die because of walls snake 2
                if(snake.intersects(snake2.getBoundsInLocal())){System.out.println("die 5");return true;}//die because of intersection between snakes
                if(snake2.intersects(snake.getBoundsInLocal())){System.out.println("die 6");return true;}//die because of intersection between snakes
            }
            if (snake.checkIfEatSelf()) {                      //just for debugging
                System.out.println("die 7");                                        //die because of intersection with self
            }
            return snake.checkIfEatSelf();
        }
        //+++++++++++++++++++++++++++++++++++++++updates the game state+++++++++++++++++++++++++++++++++++++++++++++++++
        //---------------------------------gets called in Runnable interface--------------------------------------------
        public void updateGame() {
            Platform.runLater(()-> {            //braucht man wenn ein anderer Thread (other than the creator) Änderungen machen könnte
                snake.step();
                handleSnakeOutsideWalls(snake);
                if(twoPlayer){snake2.step();}
                if(twoPlayer){
                    handleSnakeOutsideWalls(snake2);}
                if (checkIfGameOver()) {                                //gameOver Screen öffnen
                    try {
                        switchToGameOver();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                else if(snake.hitFood(food)){
                    snake.eat(food);
                    if (snake.getLength()<=10){speed=speed-7;}
                    else if (snake.getLength()<=20){speed=speed-4;}
                    else if (snake.getLength()>20){speed=speed-1;}
                    else if (snake.getLength()>30){}
                    score.setText(""+snake.getLengthString());
                    newFood();
                    if(snake.getLength()%2!=0){
                        newBomb();
                    }
                    if(snake.getLength()%2==0){         //Make obstacles
                        newObstacle();
                    }
                }
                else if (twoPlayer&&(snake2.hitFood(food))){
                    snake2.eat(food);
                    if (snake2.getLength()<=10){speed=speed-7;}
                    else if (snake2.getLength()<=20){speed=speed-4;}
                    else if (snake2.getLength()>20){speed=speed-1;}
                    score.setText(""+snake2.getLengthString());
                    newFood();
                    if(snake.getLength()%2!=0){
                        newBomb();
                    }
                    if(snake2.getLength()%2==0){         //Make obstacles
                        newObstacle();
                    }
                }
                else if(snake.hitBomb()){
                    snake.decrease((AnchorPane) root);
                }
                else if(twoPlayer&&snake2.hitBomb()){
                    snake2.decrease((AnchorPane) root);
                }
            });
        }
    //++++++++++++++++++++++++++++++++++++start method++++++++++++++++++++++++++++++++++++++++++++++++++
        @Override
        public void start(Stage stage) throws IOException {
            //++++++++++++++++++++++++++++++++++++load Screen and Game++++++++++++++++++++++++++++++++++++++++++++++++++
            root = FXMLLoader.load(App.class.getResource("Game.fxml"));
            Rectangle rect = new Rectangle(100, 20, 560, 560);
            Color c= Color.rgb(58, 14, 14);
            rect.setFill(c);
            root.getChildren().add(rect);
            rect.setVisible(false);
            if(MenuController.isDarkMode()){
                rect.setVisible(true);
            }
            score= new Text(48,85,"0");
            root.getChildren().add(score);
            random = new Random();

            twoPlayer=MenuController.isMultiplayer();
            newSnake();
            newFood();
            newBomb();
            //Sound.playSound();

            //set scene
            Scene scene = new Scene(root);

            //Runnable interface of thread
            Runnable r = () -> {
                try {
                    while(!checkIfGameOver()){
                        updateGame();
                        Thread.sleep(speed);
                    }
                }catch (InterruptedException ie){
                }
            };
            //++++++++++++++++++++++++++++++++++++ events when key gets pressed ++++++++++++++++++++++++++++++++++++++++++++++++++
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

            //screen settings
            stage.setTitle("snake");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();

            //make Thread and start it:
            th = new Thread(r);
            th. setDaemon(true);
            th.start();
        }
        //MMMAAAAIIIIINNNN:
        public static void main(String[] args) {
            launch();
        }
    }