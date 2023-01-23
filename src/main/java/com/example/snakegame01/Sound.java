package com.example.snakegame01;

import javax.sound.sampled.*;
import java.net.URL;

public class Sound {
    public static Sound playSound;
    public static Sound ifEats;
    public static Sound gameOverSound;
    public static Sound wonGame;
    public static Clip music;
    static boolean check;
    static URL[] File = new URL[4];
    private static boolean gameWon;

    public Sound() {
        File[0] = getClass().getResource("soundSnakeGame.wav");
        File[1] = getClass().getResource("EATING.wav");
        File[2] = getClass().getResource("GameOverSound.wav");
        File[3] = getClass().getResource("winSound.wav");

    }

    public static void playSound(){
        try{
            //Java format to open sound file
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(File[0]);
            music = AudioSystem.getClip();
            music.open(audioInputStream);
           // if(check){ wenn es generell klappen sollte
                music.start();
                music.loop(Clip.LOOP_CONTINUOUSLY);
          //  } else music.stop();
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
        public static void gameOverSound(){
        try{
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(File[2]);
            Clip deadGame = AudioSystem.getClip();
            deadGame.open(audioInputStream);
            //if (gameOver == true){ in App implementiert
                deadGame.start();
           // }
        }catch (Exception e){
            e.printStackTrace();
        }
        }
        public static void wonGame(){
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(File[3]);
            Clip winner = AudioSystem.getClip();
            winner.open(audioInputStream);
            if (gameWon==true){
                winner.start();
            }
        //}
        }catch (Exception e){
            e.printStackTrace();
        }
        }
    }

// Youtube: https://youtu.be/5_89GjL5AhY
// Youtube: https://youtu.be/0y3NWkgxWxI
// Youtube: https://youtu.be/CQeezCdF4mk
// Youtube: https://youtu.be/VJ8FQSh-H4U

