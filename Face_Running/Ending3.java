import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import javax.sound.sampled.Clip;
import javax.swing.JFrame;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

class Ending3 extends Ending{
    private final MoviePanel Movie;
    private final Media media;
    private final MediaPlayer player;
    private int Direction;
    private int EndFlag;
    private float Alpha;

    Ending3(JFrame Frame,BufferStrategy Strategy,Clip[] SoundArray,BufferedImage[] ImageArray,String[] TextArray,int WINDOW_W,int WINDOW_H){
        super(Frame,Strategy,SoundArray,ImageArray,TextArray,WINDOW_W,WINDOW_H);
        Direction = 0;
        EndFlag = 0;
        Alpha = 1.0f;
        Movie = new MoviePanel("File/Ending3.mp4",Frame);
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
        soundPlay(7);
        if(Face.getXX() <= 750){
            Direction = 16;
        }else{
            Direction = -16;
        }
        Frame.addKeyListener(new KeyAdapter(){
            @Override
            public void keyPressed(KeyEvent e){
                if(e.getKeyCode() == KeyEvent.VK_Z){
                    skipORend();
                }
            }
        });
        Timeline timer = new Timeline(60);
        timer.getKeyFrames().add(new KeyFrame(Duration.millis(16),e->End3DrawIng()));
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
        while(SoundArray[7].getFramePosition() < SoundArray[7].getFrameLength()-75000){
            System.out.print("");
        }
        EndFlag = 1;
        while(Alpha > 0){
            System.out.print("");
        }
        timer.stop();
        try{
            Thread.sleep(1000);
        }catch(InterruptedException ignored){
        }
        moviePlay();
        EndFlag = 2;
        while(EndFlag == 2){
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
        EndFlag = 3;
        while(EndFlag != 4){
            System.out.print("");
        }
        Direction = 0;
        EndFlag = 0;
        Alpha = 1.0f;
        Frame.removeKeyListener(Frame.getKeyListeners()[0]);
        soundStop();
        timer.stop();
    }

    private void skipORend(){
        if(EndFlag == 0){
            SoundArray[7].setFramePosition(SoundArray[7].getFrameLength()-235000);
            EndFlag = 114514;
        }else if(EndFlag == 2){
            SoundArray[8].setFramePosition(1610000);
            movieEnd();
        }else if(EndFlag == 3){
            EndFlag = 4;
        }
    }

    private void End3DrawIng(){
        Graphics2D g = (Graphics2D)Strategy.getDrawGraphics();
        g.setBackground(Color.BLACK);
        g.clearRect(0,0,WINDOW_W,WINDOW_H);
        if(SoundArray[7].getFramePosition() < SoundArray[7].getFrameLength()-235000){
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
            Face.setXY2(Direction);
            Face.rotateImage2(g,16);
            Face.showImage(g);
        }else{
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,Alpha));
            g.drawImage(ImageArray[8],0,0,1500,1000,null);
        }
        if(EndFlag == 1) Alpha = Math.max(Alpha-0.008f,0.0f);
        g.dispose();
        Strategy.show();
    }

    private void moviePlay(){
        Movie.setBounds(0,0,media.getWidth(),media.getHeight());
        Movie.setVisible(true);
        player.play();
        soundPlay(8);
        Frame.repaint();
    }

    private void movieEnd(){
        player.stop();
        Movie.setVisible(false);
        EndFlag = 3;
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

    private void soundStop(){
        SoundArray[8].stop();
        SoundArray[8].flush();
        SoundArray[8].setFramePosition(0);
    }

}
