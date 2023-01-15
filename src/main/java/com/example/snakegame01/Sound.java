package com.example.snakegame01;

import javafx.scene.layout.AnchorPane;

import javax.sound.sampled.*;
import java.io.File;
public class Sound {
    public static Sound playSound;
    private static App clip;
    private Clip music;
    public static Sound ifEats;


    public static void playSound() throws Exception {
        try {
            Clip clip = AudioSystem.getClip();
            AudioInputStream audioInputStream =
                    AudioSystem.getAudioInputStream(new File("C:\\Users\\flori\\IdeaProjects\\SnakeGame01\\src\\main\\resources\\sound snake game.wav"));
            ((Clip) clip).open(audioInputStream);
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void ifEats() throws Exception{
        try {
            Clip clipEats = AudioSystem.getClip();
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("C:\\Users\\flori\\IdeaProjects\\SnakeGame01\\src\\main\\resources\\EATING.wav"));
            ((Clip)clipEats).open(audioInputStream);
            clipEats.start();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
// Youtube: https://youtu.be/5_89GjL5AhY

