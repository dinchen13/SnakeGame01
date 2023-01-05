package com.example.snakegame01;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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

        private static final int WIDTH =680;
        private static final int HEIGHT =600;
        private static final int RADIUS =8;
        private Pane root;
        private Snake snake;
        private Food food;
        private int speed;
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
            food = new Food(random.nextInt(460)+100+RADIUS,random.nextInt(460)+20+RADIUS, (AnchorPane) root,RADIUS);
        }
        //adjust food
        private void moveFoodInsideSnake(){
            food.moveFood();
            while (isFoodInsideSnake()){
                food.moveFood();
            }
        }

        private boolean isFoodInsideSnake(){
            for (int i = 0; i < snake.getLength(); i++) {
                if((food.getPositionX() == snake.getTailPositionX(i)) && (food.getPositionY() == snake.getTailPositionY(i))){
                    return true;
                }
            }
            return false;
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
            //FXMLLoader scene01 = new FXMLLoader(HelloApplication.class.getResource("Game.fxml"));
            //Scene scene = new Scene(scene01.load(), 680, 600);

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