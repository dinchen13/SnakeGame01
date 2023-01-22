package com.example.snakegame01;

import javax.sound.sampled.*;
import java.net.URL;

public class Sound {
    public static Sound playSound;
    public static Sound ifEats;
    public static Clip music;
    static boolean check;
    static URL[] File = new URL[2];

    public Sound() {
        File[0] = getClass().getResource("soundSnakeGame.wav");
        File[1] = getClass().getResource("EATING.wav");

    }

    public static void playSound(){
        try{
            //Java format to open sound file
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(File[0]);
            music = AudioSystem.getClip();
            music.open(audioInputStream);
            if(check){
                music.start();
                music.loop(Clip.LOOP_CONTINUOUSLY);
            } else music.stop();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
        public static void ifEats(){
            try {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(File[1]);
                Clip clipEats = AudioSystem.getClip();
                clipEats.open(audioInputStream);
                clipEats.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


// Youtube: https://youtu.be/5_89GjL5AhY

