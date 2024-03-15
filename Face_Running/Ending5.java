import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import javax.sound.sampled.Clip;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

class Ending5 extends Ending{
    private final MoviePanel Movie;
    private final Media media;
    private final MediaPlayer player;
    private int EndFlag;

    Ending5(JFrame Frame,BufferStrategy Strategy,Clip[] SoundArray,BufferedImage[] ImageArray,String[] TextArray,int WINDOW_W,int WINDOW_H){
        super(Frame,Strategy,SoundArray,ImageArray,TextArray,WINDOW_W,WINDOW_H);
        EndFlag = 0;
        Movie = new MoviePanel("File/Ending5.mp4",Frame);
        player = Movie.getPlayer();
        media = Movie.getMedia();
        while(player.getStatus() != MediaPlayer.Status.READY){
            System.out.print("");
        }
        Frame.setLayout(null);
        Frame.add(Movie);
        Movie.setVisible(false);
    }

    void setGameData(Founder_Face Face,ArrayList<Enemy> EnemyList,int Score,HashMap<Integer,ArrayList<Star>> StarMap){
        this.Face = Face;
        this.EnemyList = EnemyList;
        Ending.Score = Score;
        this.StarMap = StarMap;
    }

    void play(){
        soundPlay(11);
        Frame.addKeyListener(new KeyAdapter(){
            @Override
            public void keyPressed(KeyEvent e){
                if(e.getKeyCode() == KeyEvent.VK_Z){
                    skipORend();
                }
            }
        });
        Face.Five();
        Timeline timer = new Timeline(60);
        timer.getKeyFrames().add(new KeyFrame(Duration.millis(16),e->End3DrawIng()));
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
        while(SoundArray[11].getFramePosition() < 1000000){
            System.out.print("");
        }
        EndFlag = 1;
        soundStop(11);
        timer.stop();
        moviePlay();
        while(EndFlag == 1){
            if(media.getDuration().toMillis()-5000 <= player.getCurrentTime().toMillis()){
                try{
                    Thread.sleep(5000);
                    movieEnd();
                }catch(Exception ignore){
                }
                break;
            }
        }
        timer = new Timeline(60);
        timer.getKeyFrames().add(new KeyFrame(Duration.millis(16),e->End3DrawIng2()));
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
        soundPlay(12);
        EndFlag = 2;
        while(EndFlag == 2){
            System.out.print("");
        }
        EndFlag = 0;
        Frame.removeKeyListener(Frame.getKeyListeners()[0]);
        soundStop(12);
        timer.stop();
    }

    private void skipORend(){
        if(EndFlag == 0){
            soundStop(11);
            SoundArray[11].setFramePosition(1000000);
        }else if(EndFlag == 1){
            movieEnd();
            EndFlag = 2;
        }else if(EndFlag == 2){
            EndFlag = 3;
        }
    }

    private void End3DrawIng(){
        Graphics2D g = (Graphics2D)Strategy.getDrawGraphics();
        g.setBackground(Color.BLACK);
        g.clearRect(0,0,WINDOW_W,WINDOW_H);
        g.setColor(Color.WHITE);
        g.fillRect(0,850,1500,10);
        for(int i = 0; i < 6; i++){
            try{
                g.setColor(ColorArray[i]);
                ArrayList<Star> starList = StarMap.get(i);
                for(Star star : starList){
                    g.drawRect(star.getX(),star.getY(),1,1);
                }
            }catch(Exception ignored){
            }
        }
        for(Enemy enemy : EnemyList){
            try{
                enemy.showImage(g);
            }catch(Exception ignored){
            }
        }
        if(Face.getXX() < 750){
            Face.setX2(5);
        }else if(Face.getXX() > 750){
            Face.setX2(-5);
        }
        if(Face.getYY() < 500){
            Face.setY2(5);
        }else if(Face.getYY() > 500){
            Face.setY2(-5);
        }
        Face.rotateImage2(g,4);
        Face.setSize2B();
        Face.showImage(g);
        g.dispose();
        Strategy.show();
    }

    private void moviePlay(){
        Movie.setBounds(0,0,media.getWidth(),media.getHeight());
        Movie.setVisible(true);
        player.play();
        Frame.repaint();
    }

    private void movieEnd(){
        player.stop();
        Movie.setVisible(false);
        EndFlag = 2;
    }

    private void End3DrawIng2(){
        Graphics2D g = (Graphics2D)Strategy.getDrawGraphics();
        g.setBackground(Color.BLACK);
        g.clearRect(0,0,WINDOW_W,WINDOW_H);
        g.setColor(Color.WHITE);
        printScore(g);
        g.dispose();
        Strategy.show();
    }

    private void soundPlay(int index){
        SoundArray[index].setFramePosition(0);
        SoundArray[index].start();
    }

    private void soundStop(int index){
        SoundArray[index].stop();
        SoundArray[index].flush();
        SoundArray[index].setFramePosition(0);
    }

}
